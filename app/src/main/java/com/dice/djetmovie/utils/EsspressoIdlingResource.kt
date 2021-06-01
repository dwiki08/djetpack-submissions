package com.dice.djetmovie.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    //untuk instrument test ubah ke 'true'
    private const val IDLING_RESOURCE = false

    private const val RESOURCE: String = "GLOBAL"
    private val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)
    fun increment() {
        if (IDLING_RESOURCE) espressoTestIdlingResource.increment()
    }

    fun decrement() {
        if (IDLING_RESOURCE) espressoTestIdlingResource.decrement()
    }

    fun getEspressoIdlingResource(): IdlingResource {
        return espressoTestIdlingResource
    }
}