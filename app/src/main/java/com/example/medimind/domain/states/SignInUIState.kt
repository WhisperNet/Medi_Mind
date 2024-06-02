package com.example.medimind.domain.states

data class SignInUIState(
    val email: String = "",
    val password: String = "",

    val emailError: String? = null,
    val passwordError: String? = null
)
