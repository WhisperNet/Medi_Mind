package com.example.medimind.domain.states

data class SignUpUIState(
    val name: String = "",
    val address: String = "",
    val phoneNo: String = "",
    val email: String = "",
    val password: String = "",
    val otp: String = "",

    val nameError: String? = null,
    val addressError: String? = null,
    val phoneNoError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val otpError: String? = null
)
