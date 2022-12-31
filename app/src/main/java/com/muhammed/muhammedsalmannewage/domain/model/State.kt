package com.muhammed.muhammedsalmannewage.domain.model

sealed class State<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success <out T> (data: T, message: String? = null): State<T>(data = data, message = message)
    class Failure <out T> (data: T? = null, message: String?) : State<T>(data = data, message = message)

    fun isSuccessful() = this is Success
}
