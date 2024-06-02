package com.example.medimind.domain.events

sealed interface AddNewReminderUIEvent {

    data class TypeChanged(val type: String): AddNewReminderUIEvent
    data class NameChanged(val name: String): AddNewReminderUIEvent
    data class DateChanged(val date: String): AddNewReminderUIEvent
    data class TimeChanged(val time: String): AddNewReminderUIEvent
    data class StockChanged(val stock: String): AddNewReminderUIEvent
    data object SaveButtonClicked: AddNewReminderUIEvent
    data object GoBackButtonClicked: AddNewReminderUIEvent

}