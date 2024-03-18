package com.example.medimind.domain.repository

import com.example.medimind.data.model.Event
import com.example.medimind.data.model.User
import com.example.medimind.domain.model.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val currentUser: FirebaseUser?
    fun getUser(email: String): Flow<Response<User>>
    fun getEvent(email: String): Flow<List<Event>>

    fun getMedication(email: String): Flow<List<Event>>
    fun createEvent(event: Event, email: String): Flow<Response<Event>>
}