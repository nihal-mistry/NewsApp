package com.nihalmistry.newsapp.utils

import java.io.IOException

// Utility function to call a suspend function and fail safely
suspend fun <T: Any> safeApiCall(call: suspend () -> Result<T>) : Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        return Result.Error(IOException(e.message, e))
    }
}