package com.example.medimind.presentation.auth.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medimind.data.model.User
import com.example.medimind.data.model.api_response.RequestOTPResponse
import com.example.medimind.data.model.api_response.VerifyOTPResponse
import com.example.medimind.domain.events.SignUpUIEvent
import com.example.medimind.domain.model.DataState
import com.example.medimind.domain.repository.AuthRepository
import com.example.medimind.domain.repository.BdAppsApiRepository
import com.example.medimind.domain.repository.UserPref
import com.example.medimind.domain.rules.Validator
import com.example.medimind.domain.states.SignUpUIState
import com.example.medimind.strings.INVALID_OPERATOR
import com.example.medimind.strings.REGISTERED
import com.example.medimind.strings.SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val bdAppsApiRepo: BdAppsApiRepository,
    private val userPref: UserPref
) : ViewModel() {

    private val _signUpUIState = MutableStateFlow(SignUpUIState())
    val signUpUIState = _signUpUIState.asStateFlow()

    private val _navigationState = MutableStateFlow(false)
    val navigationState = _navigationState.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()


    private val _otpScreenState = MutableStateFlow(false)
    val otpScreenState = _otpScreenState.asStateFlow()


    private val _requestOTP = MutableStateFlow<RequestOTPResponse?>(null)
    private val requestOTP = _requestOTP.asStateFlow()

    private val _verifyOTP = MutableStateFlow<VerifyOTPResponse?>(null)
    private val verifyOTP = _verifyOTP.asStateFlow()

    fun onEvent(event: SignUpUIEvent) {
        when (event) {
            is SignUpUIEvent.NameChanged -> {
                _signUpUIState.value = signUpUIState.value.copy(name = event.name)
            }

            is SignUpUIEvent.AddressChanged -> {
                _signUpUIState.value = signUpUIState.value.copy(address = event.address)
            }

            is SignUpUIEvent.PhoneNoChanged -> {
                _signUpUIState.value = signUpUIState.value.copy(phoneNo = event.phoneNo)
            }

            is SignUpUIEvent.EmailChanged -> {
                _signUpUIState.value = signUpUIState.value.copy(email = event.email)
            }

            is SignUpUIEvent.PasswordChanged -> {
                _signUpUIState.value = signUpUIState.value.copy(passwordError = event.password)
            }

            SignUpUIEvent.SignUpButtonClicked -> {
                if (validateAllStates()) {
                    requestOTP()
                }
            }

            is SignUpUIEvent.OTPChanged -> {
                _signUpUIState.value = signUpUIState.value.copy(otp = event.otp)
            }

            SignUpUIEvent.VerifyOTPButtonClicked -> {
                verifyOTP()
            }


        }
    }



    private fun requestOTP() = viewModelScope.launch {

        val phoneNo = signUpUIState.value.phoneNo

        val subscriberId = if (phoneNo[0] == '+' && phoneNo[1] == '8' && phoneNo[2] == '8') {
            phoneNo.substring(3)
        } else if (phoneNo[0] == '8' && phoneNo[1] == '8') {
            phoneNo.substring(2)
        } else {
            phoneNo
        }


        bdAppsApiRepo.requestOTP(subscriberId = subscriberId).collectLatest { response ->
            Log.d(TAG, "requestOTP: ${signUpUIState.value.phoneNo}, response: ${response.body()}")
            Log.d(TAG, "requestOTP: subscriberId: $subscriberId")

            if (response.isSuccessful) {
                _requestOTP.value = response.body()

                when (requestOTP.value?.statusDetail) {
                    SUCCESS -> {
                        _otpScreenState.update { true }
                    }
                    REGISTERED -> {
                        _signUpUIState.value =
                            signUpUIState.value.copy(phoneNoError = "User is already registered")
                    }
                    INVALID_OPERATOR -> {
                        _signUpUIState.value =
                            signUpUIState.value.copy(phoneNoError = "Provider must be a Robi operator")
                    }

                    else -> {

                    }
                }
            } else {
                Log.d(TAG, "requestOTP: ${response.errorBody()}")
            }
        }
    }


    private fun verifyOTP() = viewModelScope.launch {
        requestOTP.value?.let { requestOTPResponse ->
            bdAppsApiRepo.verifyOTP(
                referenceNo = requestOTPResponse.referenceNo,
                otp = signUpUIState.value.otp
            ).collectLatest { response ->
                if (response.isSuccessful) {
                    _verifyOTP.value = response.body()
                    if (verifyOTP.value?.statusDetail == SUCCESS) {
                        signUp()
                    } else {
                        _signUpUIState.value = signUpUIState.value.copy(otpError = "Invalid OTP")
                    }
                } else {
                    Log.d(TAG, "verifyOTP: ${response.errorBody()}")
                }
            }
        }
    }


    private fun signUp() = viewModelScope.launch(Dispatchers.IO) {
        val user = User(
            name = signUpUIState.value.name,
            address = signUpUIState.value.address,
            phoneNo = signUpUIState.value.phoneNo,
            email = signUpUIState.value.email
        )
        authRepo.signUp(signUpUIState.value.email, signUpUIState.value.password, user)
            .collectLatest {signUpState ->
                if (signUpState.data == true) {
                    _navigationState.update { true }
                    userPref.saveUserModel(user)
                } else if (signUpState.error != null) {
                    _signUpUIState.value = signUpUIState.value.copy(passwordError = signUpState.error)
                } else if (signUpState == DataState.Loading) {
                    _loadingState.update { true }
                }
            }
    }

    private fun validateAllStates(): Boolean {
        val nameResult = Validator.validateName(signUpUIState.value.name)
        val addressResult = Validator.validateAddress(signUpUIState.value.address)
        val phoneNoResult = Validator.validatePhoneNo(signUpUIState.value.phoneNo)
        val emailResult = Validator.validateEmail(signUpUIState.value.email)
        val passwordResult = Validator.validatePassword(signUpUIState.value.password)

        _signUpUIState.value = signUpUIState.value.copy(nameError = nameResult)
        _signUpUIState.value = signUpUIState.value.copy(addressError = addressResult)
        _signUpUIState.value = signUpUIState.value.copy(phoneNoError = phoneNoResult)
        _signUpUIState.value = signUpUIState.value.copy(emailError = emailResult)
        _signUpUIState.value = signUpUIState.value.copy(passwordError = passwordResult)

        return nameResult == null
                && addressResult == null
                && phoneNoResult == null
                && emailResult == null
                && passwordResult == null
    }



    companion object {
        private const val TAG = "SignUpViewModel"
    }
}