package com.example.medimind.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medimind.data.model.User
import com.example.medimind.domain.repository.AuthRepository
import com.example.medimind.util.Constants.TAG
import com.example.medimind.util.Constants.USERS_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepo: AuthRepository
): ViewModel() {


    fun signUp(email: String, password: String, user: User) = viewModelScope.launch {
        try {
            authRepo.signUp(email, password, user).collectLatest {  }
        }catch (e: Exception) {
            Log.d(TAG, "signUp: Sign Up Failed. ${e.message}")
        }
    }


    fun signIn(email: String, password: String) = viewModelScope.launch {
        try {
            authRepo.signIn(email, password).collectLatest {  }
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