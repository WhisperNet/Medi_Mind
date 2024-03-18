package com.example.medimind.data.repository

import android.util.Log
import com.example.medimind.data.model.User
import com.example.medimind.domain.model.Response
import com.example.medimind.domain.repository.AuthRepository
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
    private val firestore: FirebaseFirestore
): AuthRepository {
    override fun signUp(email: String, password: String, user: User): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            if (it != null) {
                firestore.collection(USERS_NODE).document(user.email).set(user.toMap())
            }
        }.await()
        emit(Response.Success(true))
    }.catch {
        Log.d(TAG, "signUp: ${it.localizedMessage}")
    }

    override fun signIn(email: String, password: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        auth.signInWithEmailAndPassword(email, password).await()
        emit(Response.Success(true))
    }.catch {
        Log.d(TAG, "signIn: ${it.localizedMessage}")
    }

    override fun signOut(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        auth.signOut()
        emit(Response.Success(true))
    }.catch {
        Log.d(TAG, "signOut: ${it.localizedMessage}")
    }


    companion object {
        const val TAG = "AuthRepositoryImpl"
    }
}