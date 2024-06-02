package com.example.medimind.domain.repository

import com.example.medimind.data.model.User
import com.example.medimind.domain.model.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(email: String, password: String, user: User): Flow<DataState<Boolean>>
    fun signIn(email: String, password: String): Flow<DataState<Boolean>>
    fun signOut(): Flow<DataState<Boolean>>
}