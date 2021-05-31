package com.dice.djetmovie.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dice.djetmovie.data.repository.DataRepository

class SearchViewModel(private val repo: DataRepository) : ViewModel() {

    fun getMoviesPaging(query: String? = null) =
        repo.getMoviesPaging(query).cachedIn(viewModelScope)

    fun getTvShowPaging(query: String? = null) =
        repo.getTvShowPaging(query).cachedIn(viewModelScope)

}