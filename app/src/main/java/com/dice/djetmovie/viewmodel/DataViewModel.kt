package com.dice.djetmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.utils.Resource
import com.dice.djetmovie.data.repository.DataRepository
import com.dice.djetmovie.utils.EspressoIdlingResource
import com.dice.djetmovie.utils.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(
    private val repo: DataRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    //untuk idling resource Instrument Test
    private val forTest = false

    private val _listMovie = MutableLiveData<List<Film>?>()
    val listMovie: MutableLiveData<List<Film>?> = _listMovie

    private val _listTvShow = MutableLiveData<List<Film>?>()
    val listTvShow: MutableLiveData<List<Film>?> = _listTvShow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    fun getMovies(refresh: Boolean) {
        if (listMovie.value == null || refresh) {
            if (forTest) EspressoIdlingResource.increment()

            _isLoading.postValue(true)
            viewModelScope.launch(Dispatchers.IO) {

                if (networkHelper.isNetworkConnected()) {
                    val result = repo.getMoviesRemote()
                    when (result.status) {
                        Resource.Status.SUCCESS -> {
                            _listMovie.postValue(result.data)
                            _isLoading.postValue(false)
                            if (forTest) EspressoIdlingResource.decrement()
                        }
                        Resource.Status.ERROR -> {
                            result.message?.let {
                                if (listMovie.value == null) _errorMsg.postValue(it)
                            }
                            _isLoading.postValue(false)
                            if (forTest) EspressoIdlingResource.decrement()
                        }
                    }
                } else {
                    val result = repo.getMoviesLocal()
                    if (result.data.isNullOrEmpty()) {
                        _errorMsg.postValue(null)
                    } else {
                        _listMovie.postValue(result.data)
                    }
                    _isLoading.postValue(false)
                    if (forTest) EspressoIdlingResource.decrement()
                }
            }
        }
    }

    fun getTvShows(refresh: Boolean) {
        if (listTvShow.value == null || refresh) {
            if (forTest) EspressoIdlingResource.increment()

            _isLoading.postValue(true)
            viewModelScope.launch(Dispatchers.IO) {
                if (networkHelper.isNetworkConnected()) {
                    val result = repo.getTvShowsRemote()
                    when (result.status) {
                        Resource.Status.SUCCESS -> {
                            _listTvShow.postValue(result.data)
                            _isLoading.postValue(false)
                            if (forTest) EspressoIdlingResource.decrement()
                        }
                        Resource.Status.ERROR -> {
                            result.message?.let {
                                if (listTvShow.value == null) _errorMsg.postValue(it)
                            }
                            _isLoading.postValue(false)
                            if (forTest) EspressoIdlingResource.decrement()
                        }
                    }
                } else {
                    val result = repo.getTvShowsLocal()
                    if (result.data.isNullOrEmpty()) {
                        _errorMsg.postValue(null)
                    } else {
                        _listTvShow.postValue(result.data)
                    }
                    _isLoading.postValue(false)
                    if (forTest) EspressoIdlingResource.decrement()
                }
            }
        }
    }
}