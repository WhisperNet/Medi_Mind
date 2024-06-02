package com.example.medimind.presentation.auth.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medimind.domain.events.SignInUIEvent
import com.example.medimind.domain.model.DataState
import com.example.medimind.domain.repository.AuthRepository
import com.example.medimind.domain.repository.UserPref
import com.example.medimind.domain.repository.UserRepository
import com.example.medimind.domain.rules.Validator
import com.example.medimind.domain.states.SignInUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val userRepo: UserRepository,
    private val userPref: UserPref
): ViewModel() {

    private val _signInUIState = MutableStateFlow(SignInUIState())
    val signInUIState = _signInUIState.asStateFlow()


    private val _navigationState = MutableStateFlow(false)
    val navigationState = _navigationState.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    fun onEvent(event: SignInUIEvent) {
        when (event) {
            is SignInUIEvent.EmailChanged -> {
                _signInUIState.value = signInUIState.value.copy(email = event.email)
            }

            is SignInUIEvent.PasswordChanged -> {
                _signInUIState.value = signInUIState.value.copy(password = event.password)
            }

            SignInUIEvent.SignInButtonClicked -> {
                if (validateAllStates()) {
                    signIn()
                }
            }
        }
    }


    private fun signIn() = viewModelScope.launch {
        authRepo.signIn(signInUIState.value.email, signInUIState.value.password).collectLatest { signInState ->
            if (signInState.data == true) {
                _navigationState.update { true }
                userRepo.getUser(signInUIState.value.email).collectLatest { user ->
                    if (user.error == null) {
                        user.data?.let { userPref.saveUserModel(it) }
                    }
                }
            } else if (signInState.error != null) {
                _signInUIState.value = signInUIState.value.copy(passwordError = signInState.error)
            } else if (signInState == DataState.Loading) {
                _loadingState.update { true }
            }
        }
    }


    private fun validateAllStates (): Boolean {
        val emailResult = Validator.validateEmail(signInUIState.value.email)
        val passwordResult = Validator.validatePassword(signInUIState.value.password)

        _signInUIState.value = signInUIState.value.copy(emailError = emailResult)
        _signInUIState.value = signInUIState.value.copy(passwordError = passwordResult)

        return emailResult == null && passwordResult == null
    }
}