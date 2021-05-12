package com.dice.djetmovie.utils

interface Action<T> {
    fun call(t: T)
}