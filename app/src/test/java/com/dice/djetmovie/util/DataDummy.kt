package com.dice.djetmovie.util

import com.dice.djetmovie.data.DataMapper
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.response.ResponseMovieList
import com.dice.djetmovie.data.remote.response.ResponseTvShowList
import com.dice.djetmovie.utils.Utils
import com.google.gson.Gson

object DataDummy {
    fun movieFilm(): MutableList<Film> {
        val response = Gson().fromJson(
                Utils.getJson("discover_movie_response.json"),
                ResponseMovieList::class.java
        )
        val list = mutableListOf<Film>()
        for (item in response.results) {
            list.add(DataMapper.mapMovie(item))
        }
        return list
    }

    fun tvShowFilm(): MutableList<Film> {
        val response = Gson().fromJson(
                Utils.getJson("discover_tv_response.json"),
                ResponseTvShowList::class.java
        )
        val list = mutableListOf<Film>()
        for (item in response.results) {
            list.add(DataMapper.mapTvShow(item))
        }
        return list
    }
}