package com.dice.djetmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.utils.Resource
import com.dice.djetmovie.data.repository.DataRepository
import com.dice.djetmovie.utils.Action
import com.dice.djetmovie.utils.Event
import kotlinx.coroutines.launch

class DataViewModel(private val repo: DataRepository) : ViewModel() {

    private val _resourceListFilm = MutableLiveData<Event<Resource<List<Film>>>>()
    val resourceListFilm: MutableLiveData<Event<Resource<List<Film>>>> = _resourceListFilm

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    fun getMovies() {
        _isLoading.postValue(Event(true))
        viewModelScope.launch {
            repo.getListMovie(
                    object : Action<Resource<List<Film>>> {
                        override fun call(t: Resource<List<Film>>) {
                            _resourceListFilm.postValue(Event(t))
                            _isLoading.postValue(Event(false))
                        }
                    }
            )
        }
    }

    fun getTvShows() {
        _isLoading.postValue(Event(true))
        viewModelScope.launch {
            repo.getListTvShow(
                    object : Action<Resource<List<Film>>> {
                        override fun call(t: Resource<List<Film>>) {
                            _resourceListFilm.postValue(Event(t))
                            _isLoading.postValue(Event(false))
                        }
                    }
            )
        }
    }
}