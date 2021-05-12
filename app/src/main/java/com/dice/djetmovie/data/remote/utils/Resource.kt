package com.dice.djetmovie.data.remote.utils

import com.dice.djetmovie.utils.Event

data class Resource<out T>(val status: Status, val data: T?, val message: Event<String>?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: Event<String>, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR
    }
}