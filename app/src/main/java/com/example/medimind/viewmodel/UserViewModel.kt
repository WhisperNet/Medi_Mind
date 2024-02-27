package com.example.medimind.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medimind.data.Response
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    firestoreRef: FirebaseFirestore,
    private val auth: FirebaseAuth,
): ViewModel() {

    private val userCollection = firestoreRef.collection(USERS_NODE)

    private var _userState = MutableStateFlow<Response<User>>(Response.Success(User()))
    val userState = _userState.asStateFlow()

    init {
        if(auth.currentUser != null) {
            if(auth.currentUser?.email != null) {
                viewModelScope.launch {
                    getUser(auth.currentUser?.email!!).collect {
                        _userState.value = it
                    }
                }
            }
        }
    }

    fun getUser(email: String): Flow<Response<User>> = callbackFlow {
        var response: Response<User> = Response.Loading
        var snapshotListener = ListenerRegistration {  }
        viewModelScope.launch {
            try {
                 snapshotListener = userCollection.document(email).addSnapshotListener { document, error ->
                     Log.d(TAG, "getUser: ${document?.data}")
                    if(document!=null) {
                        val name = document["name"] as String
                        val address = document["address"] as String
                        val email = document["email"] as String
                        val phoneNo = document["phone_no"] as String
                        val user = User(name, address, phoneNo, email)
                        response = Response.Success(user)
                    } else {
                        response = Response.Failure(error?.message.toString())
                    }
                     trySend(response).isSuccess
                    }
            } catch (e: Exception) {
                Log.d(TAG, "getUser: Can't get user. ${e.message}")
            }
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

}