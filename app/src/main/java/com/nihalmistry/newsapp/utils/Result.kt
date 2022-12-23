package com.nihalmistry.newsapp.utils

// Result sealed class to wrap around API Response / error objects.
sealed class Result<out T: Any> {
    data class Success<out T: Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
}