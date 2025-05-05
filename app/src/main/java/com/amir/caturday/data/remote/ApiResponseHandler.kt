package com.amir.caturday.data.remote

import com.amir.caturday.domain.model.DataState
import retrofit2.HttpException
import java.io.IOException

abstract class ApiResponseHandler {
    suspend fun <T> call(func: suspend () -> T): DataState<T> =
        try {
            val result = func()
            DataState.Success(result)
        } catch (e: IOException) {
            DataState.Failure(DataState.Failure.CODE_NETWORK_FAILURE, "Something went wrong, Check internet connection.")
        } catch (e: HttpException) {
            DataState.Failure(e.code(), "Something went wrong, Please try again later.")
        } catch (e: Exception) {
            DataState.Failure(DataState.Failure.CODE_INVALID, "Something went wrong.")
        }
}
