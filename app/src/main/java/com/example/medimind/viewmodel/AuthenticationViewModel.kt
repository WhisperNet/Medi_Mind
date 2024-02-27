package com.example.medimind.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medimind.data.User
import com.example.medimind.util.Constants.TAG
import com.example.medimind.util.Constants.USERS_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

class AuthenticationViewModel: ViewModel() {

    private val  auth = Firebase.auth

    fun signUp(email: String, password: String, user: User) = viewModelScope.launch {
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                createUser(user)
            }
        }catch (e: Exception) {
            Log.d(TAG, "signUp: Sign Up Failed. ${e.message}")
        }
    }


    fun signIn(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

            }
        } catch (e: Exception) {
            Log.d(TAG, "signIn: Sign In Failed. ${e.message}")
        }
    }


    fun signOut() {
        auth.signOut()
    }


    private fun createUser(user: User) {
        val db = Firebase.firestore
        try {
            db.collection(USERS_NODE).document(user.email).set(user.toMap())
        } catch (e: Exception) {
            Log.d(TAG, "createUser: Can't create User. ${e.message}")
        }
    }
}