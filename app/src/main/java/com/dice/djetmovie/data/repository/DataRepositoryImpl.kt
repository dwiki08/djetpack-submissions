package com.dice.djetmovie.data.repository

import android.content.Context
import com.dice.djetmovie.R
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.data.DataMapper
import com.dice.djetmovie.data.local.dao.MovieDao
import com.dice.djetmovie.data.local.dao.TvShowDao
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.ApiService
import com.dice.djetmovie.data.remote.utils.Resource
import com.dice.djetmovie.data.remote.utils.ResponseHandler
import com.dice.djetmovie.utils.Action
import com.dice.djetmovie.utils.Event
import com.dice.djetmovie.utils.NetworkHelper

class DataRepositoryImpl(
        private val context: Context,
        private val networkHelper: NetworkHelper,
        private val responseHandler: ResponseHandler,
        private val apiService: ApiService,
        private val movieDao: MovieDao,
        private val tvShowDao: TvShowDao
) : DataRepository {

    private val apiKey = Constants.API_KEY
    private val apiLanguage = Constants.API_LANGUAGE
    private val msgNoInternet = context.getString(R.string.failed_remote_message)

    override suspend fun getListMovie(resource: Action<Resource<List<Film>>>) {
        val listEntities = movieDao.getMovies() as MutableList
        val listFilm = DataMapper.mapListMovie(listEntities) as MutableList
        val isShowLocal = listFilm.isNotEmpty()

        if (isShowLocal) resource.call(Resource.success(listFilm))

        if (networkHelper.isNetworkConnected()) {
            try {
                val response = apiService.getMovie(apiKey, apiLanguage)
                if (response.isSuccessful) {
                    response.body()?.let {
                        listEntities.clear()
                        for (responseData in it.results) {
                            val entity = DataMapper.map(responseData)
                            val film = DataMapper.map(entity)
                            listEntities.add(entity)
                            listFilm.add(film)
                        }
                        movieDao.clearMovie()
                        movieDao.insertMovies(*listEntities.toTypedArray())
                        if (!isShowLocal) resource.call(responseHandler.handleSuccess(listFilm))
                    }
                } else {
                    resource.call(responseHandler.handleResponseCode(response.code(), listFilm))
                }
            } catch (e: Exception) {
                resource.call(responseHandler.handleException(e, listFilm))
            }
        } else {
            resource.call(Resource.error(Event(msgNoInternet), listFilm))
        }
    }

    override suspend fun getListTvShow(resource: Action<Resource<List<Film>>>) {
        val listEntities = tvShowDao.getTvShows() as MutableList
        val listFilm = DataMapper.mapListTvShow(listEntities) as MutableList
        val isShowLocal = listFilm.isNotEmpty()

        if (isShowLocal) resource.call(Resource.success(listFilm))

        if (networkHelper.isNetworkConnected()) {
            try {
                val response = apiService.getTvShow(apiKey, apiLanguage)
                if (response.isSuccessful) {
                    response.body()?.let {
                        listEntities.clear()
                        for (responseData in it.results) {
                            val entity = DataMapper.map(responseData)
                            val film = DataMapper.map(entity)
                            listEntities.add(entity)
                            listFilm.add(film)
                        }
                        tvShowDao.clearTvShow()
                        tvShowDao.insertTvShows(*listEntities.toTypedArray())
                        if (!isShowLocal) resource.call(responseHandler.handleSuccess(listFilm))
                    }
                } else {
                    resource.call(responseHandler.handleResponseCode(response.code(), listFilm))
                }
            } catch (e: Exception) {
                resource.call(responseHandler.handleException(e, listFilm))
            }
        } else {
            resource.call(Resource.error(Event(msgNoInternet), listFilm))
        }
    }
}