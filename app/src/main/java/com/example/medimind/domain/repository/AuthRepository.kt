package com.example.medimind.domain.repository

import com.example.medimind.data.model.User
import com.example.medimind.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(email: String, password: String, user: User): Flow<Response<Boolean>>
    fun signIn(email: String, password: String): Flow<Response<Boolean>>
    fun signOut(): Flow<Response<Boolean>>
}