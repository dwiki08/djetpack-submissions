package com.dice.djetmovie.util

import com.dice.djetmovie.data.DataMapper
import com.dice.djetmovie.data.local.entities.MovieEntity
import com.dice.djetmovie.data.local.entities.TvShowEntity
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.response.ResponseMovieList
import com.dice.djetmovie.data.remote.response.ResponseTvShowList
import com.dice.djetmovie.utils.Utils
import com.google.gson.Gson

object DataDummy {
    fun dummyMovieFilm(): MutableList<Film> {
        val list = mutableListOf<Film>()
        for (item in dummyMovieEntity()) {
            list.add(DataMapper.map(item))
        }
        return list
    }

    fun dummyMovieEntity(): MutableList<MovieEntity> {
        val response = Gson().fromJson(
            Utils.getJson("discover_movie_response.json"),
            ResponseMovieList::class.java
        )
        val list = mutableListOf<MovieEntity>()
        for (item in response.results) {
            list.add(DataMapper.map(item))
        }
        return list
    }

    fun dummyTvShowFilm(): MutableList<Film> {
        val list = mutableListOf<Film>()
        for (item in dummyTvShowEntity()) {
            list.add(DataMapper.map(item))
        }
        return list
    }

    fun dummyTvShowEntity(): MutableList<TvShowEntity> {
        val response = Gson().fromJson(
            Utils.getJson("discover_tv_response.json"),
            ResponseTvShowList::class.java
        )
        val list = mutableListOf<TvShowEntity>()
        for (item in response.results) {
            list.add(DataMapper.map(item))
        }
        return list
    }
}