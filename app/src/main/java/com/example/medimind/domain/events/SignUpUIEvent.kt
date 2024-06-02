package com.example.medimind.domain.events

sealed interface SignUpUIEvent {
    data class NameChanged(val name: String): SignUpUIEvent
    data class AddressChanged(val address: String): SignUpUIEvent
    data class PhoneNoChanged(val phoneNo: String): SignUpUIEvent
    data class EmailChanged(val email: String): SignUpUIEvent
    data class PasswordChanged(val password: String): SignUpUIEvent
    data class OTPChanged(val otp: String): SignUpUIEvent

    data object SignUpButtonClicked: SignUpUIEvent
    data object VerifyOTPButtonClicked: SignUpUIEvent
}