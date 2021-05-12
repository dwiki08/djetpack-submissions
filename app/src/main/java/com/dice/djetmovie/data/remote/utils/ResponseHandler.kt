package com.dice.djetmovie.data.remote.utils

import com.dice.djetmovie.utils.Event
import retrofit2.HttpException
import java.net.SocketTimeoutException

open class ResponseHandler {
    enum class ErrorCodes(val code: Int) {
        SocketTimeOut(-1)
    }

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception, data: T): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(Event(getErrorMessage(e.code())), data)
            is SocketTimeoutException -> Resource.error(Event(getErrorMessage(ErrorCodes.SocketTimeOut.code)), data)
            else -> Resource.error(Event(getErrorMessage(Int.MAX_VALUE)), data)
        }
    }

    fun <T : Any> handleResponseCode(code: Int, data: T): Resource<T> {
        return Resource.error(Event(getErrorMessage(code)), data)
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