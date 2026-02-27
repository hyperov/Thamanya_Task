package com.nabil.ahmed.thamanyatask.utils

sealed interface ApiState<out T> {
    data object Loading : ApiState<Nothing>
    data class Success<out T>(val data: T) : ApiState<T>
    data class Error(val message: String) : ApiState<Nothing>
    data object Empty : ApiState<Nothing>
}
