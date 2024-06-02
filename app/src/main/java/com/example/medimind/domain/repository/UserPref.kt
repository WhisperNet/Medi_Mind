package com.example.medimind.domain.repository


import com.example.medimind.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserPref {
    suspend fun saveUserModel(userModel: User)

    fun getUserModel(): Flow<User>
}