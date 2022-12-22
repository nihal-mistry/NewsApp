package com.nihalmistry.newsapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher = Main,
    val computation: CoroutineDispatcher = Default,
    val io: CoroutineDispatcher = IO
)