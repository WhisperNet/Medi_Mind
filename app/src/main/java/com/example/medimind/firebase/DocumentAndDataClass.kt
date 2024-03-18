package com.example.medimind.firebase

import com.example.medimind.data.model.Event
import com.example.medimind.data.model.User
import com.google.firebase.firestore.DocumentSnapshot

object DocumentAndDataClass {

    fun getUserFromFirebase(document: DocumentSnapshot): User {
        val name = document["name"].toString()
        val address = document["address"].toString()
        val email = document["email"].toString()
        val phoneNo = document["phone_no"].toString()
        return  User(name, address, phoneNo, email)
    }

    fun getEventFromFirebase(document: DocumentSnapshot): Event {
        val type = document["type"].toString()
        val name = document["name"].toString()
        val date = document["date"].toString()
        val time = document["time"].toString()
        val stock = document["stock"] as Long

        return Event(
            type = type,
            name = name,
            date = date,
            time = time,
            stock = stock.toInt()
        )
    }
 }