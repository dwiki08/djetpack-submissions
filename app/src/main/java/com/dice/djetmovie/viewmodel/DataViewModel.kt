package com.dice.djetmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dice.djetmovie.repository.Constants
import com.dice.djetmovie.repository.FilmDummy
import com.dice.djetmovie.repository.model.Film

class DataViewModel : ViewModel() {

    val listFilms = MutableLiveData<List<Film>>()

    fun getFilms(filmType: String?) {
        val listFilms = mutableListOf<Film>()

        when (filmType) {
            Constants.FILM_TYPE_MOVIE -> {
                listFilms.addAll(FilmDummy.generateMovies())
            }
            Constants.FILM_TYPE_TV_SHOW -> {
                listFilms.addAll(FilmDummy.generateTvShows())
            }
        }

        this.listFilms.postValue(listFilms)
    }
}