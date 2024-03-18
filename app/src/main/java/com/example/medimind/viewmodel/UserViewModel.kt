package com.example.medimind.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medimind.data.model.Medication
import com.example.medimind.domain.model.Response
import com.example.medimind.data.model.User
import com.example.medimind.domain.repository.UserRepository
import com.example.medimind.firebase.DocumentAndDataClass.getMedicationFromFirebase
import com.example.medimind.firebase.DocumentAndDataClass.getUserFromFirebase
import com.example.medimind.util.Constants.USERS_NODE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.medimind.util.Constants.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepository
): ViewModel() {


    private var _userState = MutableStateFlow<Response<User>>(Response.Success(User()))
    val userState = _userState.asStateFlow()

    private val _medicationItems = MutableStateFlow<List<Medication>>(listOf())
    val medicationItems = _medicationItems.asStateFlow()

    init {
        val currentUser = userRepo.currentUser
        if(currentUser != null && currentUser.email != null) {
            getUser(currentUser.email!!)
        }
    }

    private fun getUser(email: String) = viewModelScope.launch {
        userRepo.getUser(email).collectLatest {
            _userState.value = it
        }
    }

}