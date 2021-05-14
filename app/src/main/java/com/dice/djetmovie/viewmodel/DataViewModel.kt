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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(private val repo: DataRepository) : ViewModel() {

    private val _listMovie = MutableLiveData<Resource<List<Film>>>()
    val listMovie: MutableLiveData<Resource<List<Film>>> = _listMovie

    private val _listTvShow = MutableLiveData<Resource<List<Film>>>()
    val listTvShow: MutableLiveData<Resource<List<Film>>> = _listTvShow

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    fun getMovies(refresh: Boolean = false) {
        if (listMovie.value == null || refresh) {
            _isLoading.postValue(Event(true))
            viewModelScope.launch(Dispatchers.IO) {
                repo.getListMovie(object : Action<Resource<List<Film>>> {
                    override fun call(t: Resource<List<Film>>) {
                        _listMovie.postValue(t)
                        _isLoading.postValue(Event(false))
                    }
                }
                )
            }
        }
    }

    fun getTvShows(refresh: Boolean = false) {
        if (listMovie.value == null || refresh) {
            _isLoading.postValue(Event(true))
            viewModelScope.launch(Dispatchers.IO) {
                repo.getListTvShow(object : Action<Resource<List<Film>>> {
                    override fun call(t: Resource<List<Film>>) {
                        _listTvShow.postValue(t)
                        _isLoading.postValue(Event(false))
                    }
                }
                )
            }
        }
    }
}