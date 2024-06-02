package com.example.medimind.data.repository

import android.util.Log
import com.example.medimind.data.model.Event
import com.example.medimind.data.model.User
import com.example.medimind.domain.model.DataState
import com.example.medimind.domain.repository.UserRepository
import com.example.medimind.firebase.DocumentAndDataClass.getEventFromFirebase
import com.example.medimind.firebase.DocumentAndDataClass.getUserFromFirebase
import com.example.medimind.util.Constants.USERS_NODE
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): UserRepository {

    override fun getUser(email: String): Flow<DataState<User>> = callbackFlow {
        val snapshotListener = firestore.collection(USERS_NODE)
            .document(email).addSnapshotListener { value, _ ->
                if (value!=null) {
                    val user = getUserFromFirebase(value)
                    trySend(DataState.Success(user)).isSuccess
                }
            }

        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getUser: ${it.localizedMessage}")
    }

    override fun createEvent(event: Event, email: String): Flow<DataState<Event>> = flow {
        emit(DataState.Loading)
        firestore.collection(USERS_NODE).document(email).collection(if (event.type == "Vaccination") "Event" else event.type)
            .document(event.name).set(event.toMap()).await()
        emit(DataState.Success(event))
    }.catch {
        Log.d(TAG, "createEvent: ${it.localizedMessage}")
    }

    override fun updateMedicationStock(eventId: String, email: String, currentStock: Int): Flow<DataState<Boolean>> = flow {
        firestore.collection(USERS_NODE).document(email).collection("Medication")
            .document(eventId).update("stock",currentStock)
    }

    override fun getEvent(email: String): Flow<List<Event>> = callbackFlow {

        val snapshotListener =
        firestore.collection(USERS_NODE).document(email).collection("Event").addSnapshotListener { value, _ ->
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
            firestore.collection(USERS_NODE).document(email).collection("Medication").addSnapshotListener { value, _ ->
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