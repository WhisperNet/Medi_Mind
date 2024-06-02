package com.example.medimind.domain.events

sealed interface HomeUIEvent {
    data object AddNewButtonClicked: HomeUIEvent
    data object MenuIconClicked: HomeUIEvent
    data object CloseStateClicked: HomeUIEvent
    data object SignOutButtonClicked: HomeUIEvent
}