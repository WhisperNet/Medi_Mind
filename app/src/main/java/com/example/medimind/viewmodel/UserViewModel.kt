package com.example.medimind.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medimind.data.User
import com.example.medimind.util.Constants.USERS_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import com.example.medimind.util.Constants.TAG

class UserViewModel: ViewModel() {

    private val firestoreRef = Firebase.firestore
    private val auth = Firebase.auth
    private val userCollection = firestoreRef.collection(USERS_NODE)

    private var _userState = MutableStateFlow(User())
    val userState = _userState.asStateFlow()
    init {
        if(auth.currentUser != null) {
            if(auth.currentUser?.email != null) {
                getUser(auth.currentUser?.email!!)
            }
        }
    }

    fun getUser(email: String): User {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userCollection.document(email).get().addOnSuccessListener { document ->
                    _userState.value = document.toObject(User::class.java)!!
                }
            } catch (e: Exception) {
                Log.d(TAG, "getUser: Can't get user. ${e.message}")
            }
        }
        return _userState.value;
    }

}