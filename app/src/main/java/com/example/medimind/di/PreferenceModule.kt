package com.example.medimind.di

import android.content.Context
import com.example.medimind.data.repository.UserPrefImpl
import com.example.medimind.domain.repository.UserPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PreferenceModule {

    @Singleton
    @Provides
    fun providesUserPreferences(
        @ApplicationContext context: Context
    ): UserPref = UserPrefImpl(context)
}