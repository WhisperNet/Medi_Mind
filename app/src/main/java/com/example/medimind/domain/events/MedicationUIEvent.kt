package com.example.medimind.domain.events

sealed interface MedicationUIEvent {

    data class RestockButtonClicked(val eventId: String, val currentStock: Int): MedicationUIEvent
    data class DecreaseStockButtonClicked(val eventId: String, val currentStock: Int): MedicationUIEvent
    data object AddNewButtonClicked: MedicationUIEvent
}