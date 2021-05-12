package com.dice.djetmovie.data.repository

import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.utils.Resource
import com.dice.djetmovie.utils.Action

interface DataRepository {
    suspend fun getListMovie(resource: Action<Resource<List<Film>>>)

    suspend fun getListTvShow(resource: Action<Resource<List<Film>>>)
}