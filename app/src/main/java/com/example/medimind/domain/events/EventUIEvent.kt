package com.example.medimind.domain.events

sealed interface EventUIEvent {
    data object AddNewButtonClicked: EventUIEvent
}