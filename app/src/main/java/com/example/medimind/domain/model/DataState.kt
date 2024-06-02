package com.example.medimind.domain.model

sealed class DataState<out T>(
    val data: T? = null,
    val error: String? = null
) {
    data object Loading: DataState<Nothing>()

    data class Success<out T>(val info: T? = null): DataState<T>(data = info)
    data class Failure<out E>(val message: String): DataState<E>(error = message)
}