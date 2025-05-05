package com.amir.caturday.domain.model

sealed class DataState<out T> {
    class Success<out D>(
        val data: D,
    ) : DataState<D>()

    class Failure(
        val code: Int,
        val message: String,
    ) : DataState<Nothing>() {
        companion object {
            const val CODE_INVALID = -1001
            const val CODE_NOT_FOUND = -1002
            const val CODE_NETWORK_FAILURE = -1003
        }
    }

    data object Loading : DataState<Nothing>()
}

fun <I, O> DataState<I>.toDomain(func: I.() -> O): DataState<O> =
    when (this) {
        is DataState.Failure -> this
        is DataState.Loading -> this
        is DataState.Success -> DataState.Success(data.func())
    }
