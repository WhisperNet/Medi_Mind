package com.example.medimind.domain.repository

import com.example.medimind.data.model.Event
import com.example.medimind.data.model.User
import com.example.medimind.domain.model.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(email: String): Flow<DataState<User>>
    fun getEvent(email: String): Flow<List<Event>>

    fun getMedication(email: String): Flow<List<Event>>
    fun createEvent(event: Event, email: String): Flow<DataState<Event>>
    fun updateMedicationStock(eventId: String, email: String, currentStock: Int): Flow<DataState<Boolean>>
}