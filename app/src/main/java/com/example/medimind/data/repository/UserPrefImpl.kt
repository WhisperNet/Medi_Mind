package com.example.medimind.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.medimind.data.model.User
import com.example.medimind.domain.repository.UserPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPrefImpl (private val context: Context) : UserPref {

    companion object {
        private val NAME = stringPreferencesKey("NAME")
        private val ADDRESS = stringPreferencesKey("ADDRESS")
        private val PHONE_NO = stringPreferencesKey("PHONE_NO")
        private val EMAIL = stringPreferencesKey("EMAIL")
        private const val DATASTORE_NAME = "USER"
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
    }

    override suspend fun saveUserModel(userModel: User) {
        context.datastore.edit { users ->
            users[NAME] = userModel.name
            users[ADDRESS] = userModel.address
            users[PHONE_NO] = userModel.phoneNo
            users[EMAIL] = userModel.email
        }
    }

    override fun getUserModel(): Flow<User> = context.datastore.data.map { user ->
        User(
            name = user[NAME] ?: "",
            address = user[ADDRESS] ?: "",
            phoneNo = user[PHONE_NO] ?: "",
            email = user[EMAIL] ?: "",

        )
    }
}