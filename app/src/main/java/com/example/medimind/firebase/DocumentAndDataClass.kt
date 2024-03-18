package com.example.medimind.firebase

import com.example.medimind.data.model.Medication
import com.example.medimind.data.model.User
import com.google.firebase.firestore.DocumentSnapshot

object DocumentAndDataClass {

    fun getUserFromFirebase(document: DocumentSnapshot): User {
        val name = document["name"] as String
        val address = document["address"] as String
        val email = document["email"] as String
        val phoneNo = document["phone_no"] as String
        return  User(name, address, phoneNo, email)
    }

    fun getMedicationFromFirebase(document: DocumentSnapshot): Medication {
        val name = document["name"].toString()
        val hour = document["hour"].toString()
        val minute = document["minute"].toString()
        val available = document["available"] as Int

        return Medication(name, hour, minute, available)
    }
 }