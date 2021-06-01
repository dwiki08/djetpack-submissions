package com.dice.djetmovie.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dice.djetmovie.data.repository.DataRepository

class MainViewModel(private val repo: DataRepository) : ViewModel() {

    fun getMoviesPaging() = repo.getMoviesPaging().cachedIn(viewModelScope)

    fun getTvShowPaging() = repo.getTvShowPaging().cachedIn(viewModelScope)
}