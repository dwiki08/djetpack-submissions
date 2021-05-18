package com.dice.djetmovie.data.repository

import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.utils.Resource

interface DataRepository {
    suspend fun getMoviesRemote(): Resource<List<Film>>

    suspend fun getMoviesLocal(): Resource<List<Film>>

    suspend fun getTvShowsRemote(): Resource<List<Film>>

    suspend fun getTvShowsLocal(): Resource<List<Film>>
}