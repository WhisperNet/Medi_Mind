package com.example.medimind.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medimind.data.model.User
import com.example.medimind.domain.model.Response
import com.example.medimind.domain.repository.AuthRepository
import com.example.medimind.util.Constants.TAG
import com.example.medimind.util.Constants.USERS_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepo: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val uiState = _uiState.asStateFlow()
    fun signUp(email: String, password: String, user: User) = viewModelScope.launch {
        try {
            authRepo.signUp(email, password, user).collectLatest {
                _uiState.value = it
            }
        }catch (e: Exception) {
            Log.d(TAG, "signUp: Sign Up Failed. ${e.message}")
        }
    }


    fun signIn(email: String, password: String) = viewModelScope.launch {
        try {
            authRepo.signIn(email, password).collectLatest {
                _uiState.value = it
            }
        } catch (e: Exception) {
            Log.d(TAG, "signIn: Sign In Failed. ${e.localizedMessage}")
        }
    }


    fun signOut() = viewModelScope.launch {
        try {
            authRepo.signOut().collectLatest {  }
        } catch (e: Exception) {
            Log.d(TAG, "signOut: ${e.localizedMessage}")
        }
    }

}