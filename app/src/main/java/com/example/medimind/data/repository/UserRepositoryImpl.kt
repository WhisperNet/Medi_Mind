package com.example.medimind.data.repository

import androidx.lifecycle.ViewModel
import com.example.medimind.data.model.User
import com.example.medimind.domain.model.Response
import com.example.medimind.domain.repository.UserRepository
import com.example.medimind.firebase.DocumentAndDataClass.getUserFromFirebase
import com.example.medimind.util.Constants.USERS_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): UserRepository {
    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun getUser(email: String): Flow<Response<User>> = callbackFlow {
        val snapshotListener = firestore.collection(USERS_NODE)
            .document(email).addSnapshotListener { value, error ->
                if (value!=null) {
                    val user = getUserFromFirebase(value)
                    trySend(Response.Success(user)).isSuccess
                }
            }

        awaitClose {
            snapshotListener.remove()
        }
    }

}