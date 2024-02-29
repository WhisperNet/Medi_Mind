package com.example.medimind.data

data class Health(
    var medicationList: List<String> = emptyList(),
    var eventList: List<Event> = emptyList()
)
