package com.example.medimind.di

import com.example.medimind.data.repository.AuthRepositoryImpl
import com.example.medimind.data.repository.UserRepositoryImpl
import com.example.medimind.domain.repository.AuthRepository
import com.example.medimind.domain.repository.UserPref
import com.example.medimind.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        userPref: UserPref
    ): AuthRepository = AuthRepositoryImpl (auth, firestore, userPref)

    @Provides
    @Singleton
    fun providesUserRepository(
        firestore: FirebaseFirestore,
    ): UserRepository = UserRepositoryImpl (firestore)
}