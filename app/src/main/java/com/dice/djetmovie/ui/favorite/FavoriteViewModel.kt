package com.dice.djetmovie.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dice.djetmovie.data.repository.DataRepository

class FavoriteViewModel(private val repo: DataRepository) : ViewModel() {

    fun getMoviesPaging() = repo.getFavoriteMovies().cachedIn(viewModelScope)

    fun getTvShowPaging() = repo.getFavoriteTvShow().cachedIn(viewModelScope)

}