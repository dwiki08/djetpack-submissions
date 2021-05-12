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

    private val _listFilm = MutableLiveData<Resource<List<Film>>>()
    val listFilm: MutableLiveData<Resource<List<Film>>> = _listFilm

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    fun getMovies() {
        _isLoading.value = Event(true)
        viewModelScope.launch {
            repo.getListMovie(
                    object : Action<Resource<List<Film>>> {
                        override fun call(t: Resource<List<Film>>) {
                            _listFilm.postValue(t)
                            _isLoading.value = Event(false)
                        }
                    }
            )
            _isLoading.value = Event(false)
        }
    }

    fun getTvShows() {
        _isLoading.value = Event(true)
        viewModelScope.launch {
            repo.getListTvShow(
                    object : Action<Resource<List<Film>>> {
                        override fun call(t: Resource<List<Film>>) {
                            _listFilm.postValue(t)
                            _isLoading.value = Event(false)
                        }
                    }
            )
            _isLoading.value = Event(false)
        }
    }
}