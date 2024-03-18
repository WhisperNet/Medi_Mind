package com.example.medimind.data.model

import com.example.medimind.data.model.Event

data class Health(
    var medicationList: List<String> = emptyList(),
    var eventList: List<Event> = emptyList()
)
