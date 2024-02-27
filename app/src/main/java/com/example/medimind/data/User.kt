package com.example.medimind.data

data class User(
    val name: String = "",
    val address: String = "",
    val phoneNo: String = "",
    val email: String = "",
) {
    fun toMap(): Map<String, Any> = mapOf(
        "name" to name,
        "address" to address,
        "phone_no" to phoneNo,
        "email" to email
    )
}
