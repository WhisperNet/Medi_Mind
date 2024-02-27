package com.example.medimind.data

sealed class Response<out T>(
    val data: T? = null,
    val error: String? = null
) {
    data object Loading: Response<Nothing>()

    data class Success<out T>(val info: T? = null): Response<T>(data = info)
    data class Failure<out E>(val message: String): Response<E>(error = message)
}