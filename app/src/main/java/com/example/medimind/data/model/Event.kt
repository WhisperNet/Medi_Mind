package com.example.medimind.data.model


data class Event (
    val type: String = "Medication",
    val name: String = "",
    val date: String = "",
    val time: String = "",
    val stock: Int = 0
) {
    fun toMap(): Map<String, Any> = mapOf(
        "type" to type,
        "name" to name,
        "date" to date,
        "time" to time,
        "stock" to stock,
    )
}