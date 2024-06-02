package com.example.medimind.domain.rules

import android.util.Patterns

object Validator {

    fun validateName(name: String): String? {
        var message: String? = null
        if(name.isBlank()) message = "Name can't be empty"

        return message
    }

    fun validateAddress(address: String): String? {
        var message: String? = null
        if(address.isBlank()) message = "Address can't be empty"

        return message
    }
    fun validateEmail(email: String): String? {
        var message: String? = null
        if(email.isBlank()) message = "Email can't be empty"
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) message = "Invalid Email"

        return message
    }

    fun validatePassword(password: String): String? {
        var message: String? = null
        val number = Regex("[0-9]")
        val smallChar = Regex("[a-z]")
        val capitalChar = Regex("[A-Z]")
        if(password.isBlank()) message = "Password can't be empty"
        else if(password.length < 8) message = "Password must contain at least 8 characters"
        else if(!number.containsMatchIn(password)) message = "Password must contain one number"
        else if(!smallChar.containsMatchIn(password) && !capitalChar.containsMatchIn(password)) message = "Password must contain one character"

        return message
    }

    fun validatePhoneNo(phoneNo: String): String? {

        var message: String? = null
        val bangladeshiPhoneNumberPattern = "^(?:\\+88|88)?01[3-9]\\d{8}$".toRegex()
        if (!phoneNo.matches(bangladeshiPhoneNumberPattern)) {
            message = "Invalid Phone No"
        }

        return message
    }

    fun validateDate(date: String): String? {
        var message: String? = null
        if(date.isBlank()) message = "Date can't be empty"

        return message
    }

    fun validateTime(time: String): String? {
        var message: String? = null
        if(time.isBlank()) message = "Time can't be empty"

        return message
    }

    fun validateStock(stock: String): String? {

        var message: String? = null
        if (stock.isBlank()) {
            message = "Stock can't be empty"
        } else{
            stock.forEach {
                if ( it != '0' && it != '1' && it != '2' && it != '3' && it != '4' && it != '5' && it != '6' && it != '7' && it != '8' && it != '9') {
                    message = "Invalid stock"
                }
            }
        }
        return message
    }
}