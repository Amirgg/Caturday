package com.amir.caturday.util

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

suspend fun <T> Flow<T>.toListWithTimeout(timeout: Long = 1000) =
    coroutineScope {
        val results = mutableListOf<T>()
        val job =
            launch {
                this@toListWithTimeout.collect {
                    results.add(it)
                }
            }
        launch {
            delay(timeout)
            job.cancel()
        }
        return@coroutineScope results
    }
