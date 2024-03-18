package com.example.medimind.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medimind.data.model.Event
import com.example.medimind.data.model.User
import com.example.medimind.domain.model.Response
import com.example.medimind.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepository
): ViewModel() {


    private var _userState = MutableStateFlow<Response<User>>(Response.Success(User()))
    val userState = _userState.asStateFlow()

    private val _medicationItems = MutableStateFlow<List<Event>>(listOf())
    val medicationItems = _medicationItems.asStateFlow()

    private val _eventItems = MutableStateFlow<List<Event>>(listOf())
    val eventItems = _eventItems.asStateFlow()

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

    fun setEvent(event: Event, email: String) = viewModelScope.launch {
        userRepo.createEvent(event, email).collectLatest {

        }
    }

    fun getMedications(email: String) = viewModelScope.launch {
        userRepo.getMedication(email).collectLatest {
            _medicationItems.value = it
        }
    }

    fun getEvents(email: String) = viewModelScope.launch {
        userRepo.getEvent(email).collectLatest {
            _eventItems.value = it
        }
    }

}