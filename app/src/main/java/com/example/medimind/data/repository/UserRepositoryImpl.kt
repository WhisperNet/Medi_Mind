package com.example.medimind.data.repository

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.medimind.data.model.Event
import com.example.medimind.data.model.User
import com.example.medimind.domain.model.Response
import com.example.medimind.domain.repository.UserRepository
import com.example.medimind.firebase.DocumentAndDataClass.getEventFromFirebase
import com.example.medimind.firebase.DocumentAndDataClass.getUserFromFirebase
import com.example.medimind.util.Constants.USERS_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.log


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
    }.catch {
        Log.d(TAG, "getUser: ${it.localizedMessage}")
    }

    override fun createEvent(event: Event, email: String): Flow<Response<Event>> = flow {
        emit(Response.Loading)
        firestore.collection(USERS_NODE).document(email).collection(event.type)
            .document(event.name).set(event.toMap()).await()
        emit(Response.Success(event))
    }

    override fun getEvent(email: String): Flow<List<Event>> = callbackFlow {

        val snapshotListener =
        firestore.collection(USERS_NODE).document(email).collection("Event").addSnapshotListener { value, error ->
            val eventSet = mutableSetOf<Event>()
            value?.documents?.forEach {
                val event = getEventFromFirebase(it)
                eventSet.add(event)
            }
            trySend(eventSet.toList()).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
        Log.d(TAG, "getEvent: ${it.localizedMessage}")
    }

    override fun getMedication(email: String): Flow<List<Event>> = callbackFlow {

        val snapshotListener =
            firestore.collection(USERS_NODE).document(email).collection("Medication").addSnapshotListener { value, error ->
                val eventSet = mutableSetOf<Event>()
                value?.documents?.forEach {
                    val event = getEventFromFirebase(it)
                    eventSet.add(event)
                }
                trySend(eventSet.toList()).isSuccess
            }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
        Log.d(TAG, "getEvent: ${it.localizedMessage}")
    }

    companion object {
        const val TAG = "UserRepositoryImpl"
    }

}