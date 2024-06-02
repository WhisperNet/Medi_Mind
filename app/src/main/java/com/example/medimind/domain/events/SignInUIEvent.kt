package com.example.medimind.domain.events

sealed interface SignInUIEvent {

    data class EmailChanged(val email: String): SignInUIEvent
    data class PasswordChanged(val password: String): SignInUIEvent

    data object SignInButtonClicked: SignInUIEvent
}