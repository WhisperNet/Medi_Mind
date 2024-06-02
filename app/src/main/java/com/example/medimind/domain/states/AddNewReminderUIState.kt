package com.example.medimind.domain.states

import com.example.medimind.util.Constants.ALL_EVENTS

data class AddNewReminderUIState(
    val type: String = ALL_EVENTS[0],
    val name: String = "",
    val date: String = "",
    val time: String = "",
    val stock: String = "",

    val nameError: String? = null,
    val dateError: String? = null,
    val timeError: String? = null,
    val stockError: String? = null
)