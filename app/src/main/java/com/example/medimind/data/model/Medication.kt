package com.example.medimind.data.model

import java.sql.Time

data class Medication(
    val name: String = "",
    val hour: String = "",
    val minute: String = "",
    val available: Int = 0
) {
}
