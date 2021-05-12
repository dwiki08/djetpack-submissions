package com.dice.djetmovie.data.remote.utils

import retrofit2.HttpException
import java.net.SocketTimeoutException

open class ResponseHandler {
    enum class ErrorCodes(val code: Int) {
        SocketTimeOut(-1)
    }

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(getErrorMessage(ErrorCodes.SocketTimeOut.code), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    fun <T : Any> handleResponseCode(code: Int): Resource<T> {
        return Resource.error(getErrorMessage(code), null)
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorized"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}