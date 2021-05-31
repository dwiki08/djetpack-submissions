package com.dice.djetmovie.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: DataRepository) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun addMovieFavorite(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addFavoriteMovie(film)
            checkIsFavorite(film)
        }
    }

    fun addTvShowFavorite(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addFavoriteTvShow(film)
            checkIsFavorite(film)
        }
    }

    fun removeMovieFavorite(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.removeFavoriteMovie(film)
            checkIsFavorite(film)
        }
    }

    fun removeTvShowFavorite(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.removeFavoriteTvShow(film)
            checkIsFavorite(film)
        }
    }

    fun checkIsFavorite(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (film.type == Film.TYPE.MOVIE) {
                repo.getFavoriteMovieById(film.id)
            } else {
                repo.getFavoriteTvShowById(film.id)
            }
            _isFavorite.postValue(result != null)
        }
    }
}