package com.example.medimind.data

data class User(
    var name: String = "",
    var address: String = "",
    var phoneNo: String = "",
    var email: String = "",
) {
    fun toMap(): Map<String, Any> = mapOf(
        "name" to name,
        "address" to address,
        "phone_no" to phoneNo,
        "email" to email
    )
}
