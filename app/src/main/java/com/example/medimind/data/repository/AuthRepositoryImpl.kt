package com.example.medimind.data.repository

import android.util.Log
import com.example.medimind.data.model.User
import com.example.medimind.domain.model.DataState
import com.example.medimind.domain.repository.AuthRepository
import com.example.medimind.domain.repository.UserPref
import com.example.medimind.util.Constants.USERS_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userPref: UserPref
) : AuthRepository {
    override fun signUp(email: String, password: String, user: User): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            if (authResult.user != null && authResult.user?.email != null) {
                firestore.collection(USERS_NODE).document(user.email).set(user.toMap())
                emit(DataState.Success(true))
                userPref.saveUserModel(user)
            } else {
                emit(DataState.Failure("Unable to Create User"))
            }
        }.catch {
            emit(DataState.Failure("Unable to Sign Up"))
            Log.d(TAG, "signUp: ${it.localizedMessage}")
        }

    override fun signIn(email: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        if (authResult.user != null && authResult.user?.email != null) {
            emit(DataState.Success(true))
            userPref.saveUserModel(User(email = email))
        }
    }.catch {
        emit(DataState.Failure("Unable to Sign In"))
        Log.d(TAG, "signIn: ${it.localizedMessage}")
    }

    override fun signOut(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        auth.signOut()
        emit(DataState.Success(true))
        userPref.saveUserModel(User())
    }.catch {
        Log.d(TAG, "signOut: ${it.localizedMessage}")
    }


    companion object {
        const val TAG = "AuthRepositoryImpl"
    }
}