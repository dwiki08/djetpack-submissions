package com.dice.djetmovie.data.repository

import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.data.DataMapper
import com.dice.djetmovie.data.local.dao.MovieDao
import com.dice.djetmovie.data.local.dao.TvShowDao
import com.dice.djetmovie.data.local.entities.MovieEntity
import com.dice.djetmovie.data.local.entities.TvShowEntity
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.ApiService
import com.dice.djetmovie.data.remote.utils.Resource
import com.dice.djetmovie.data.remote.utils.ResponseHandler
import com.dice.djetmovie.utils.Action
import com.dice.djetmovie.utils.NetworkHelper

class DataRepositoryImpl(
        private val networkHelper: NetworkHelper,
        private val responseHandler: ResponseHandler,
        private val apiService: ApiService,
        private val movieDao: MovieDao,
        private val tvShowDao: TvShowDao
) : DataRepository {

    private val apiKey = Constants.API_KEY
    private val apiLanguage = Constants.API_LANGUAGE

    override suspend fun getListMovie(resource: Action<Resource<List<Film>>>) {
        val listFilm = mutableListOf<Film>()
        for (entity in movieDao.getMovies() as List) {
            listFilm.add(DataMapper.map(entity))
        }
        if (listFilm.isNotEmpty()) {
            resource.call(responseHandler.handleSuccess(listFilm))
        }

        val showRemote = listFilm.isEmpty()

        if (networkHelper.isNetworkConnected()) {
            try {
                val response = apiService.getMovie(apiKey, apiLanguage)
                if (response.isSuccessful) {
                    response.body()?.let {
                        val listEntities = mutableListOf<MovieEntity>()
                        for (responseData in it.results) {
                            val entity = DataMapper.map(responseData)
                            val film = DataMapper.map(entity)
                            listEntities.add(entity)
                            listFilm.add(film)
                        }
                        movieDao.insertMovies(*listEntities.toTypedArray())
                        if (showRemote) {
                            resource.call(responseHandler.handleSuccess(listFilm))
                        }
                    }
                } else {
                    resource.call(responseHandler.handleResponseCode(response.code()))
                }
            } catch (e: Exception) {
                resource.call(responseHandler.handleException(e))
            }
        }
    }

    override suspend fun getListTvShow(resource: Action<Resource<List<Film>>>) {
        val listFilm = mutableListOf<Film>()
        for (entity in tvShowDao.getTvShows() as List) {
            listFilm.add(DataMapper.map(entity))
        }
        if (listFilm.isNotEmpty()) {
            resource.call(responseHandler.handleSuccess(listFilm))
        }

        val showRemote = listFilm.isEmpty()

        if (networkHelper.isNetworkConnected()) {
            try {
                val response = apiService.getTvShow(apiKey, apiLanguage)
                if (response.isSuccessful) {
                    response.body()?.let {
                        val listEntities = mutableListOf<TvShowEntity>()
                        for (responseData in it.results) {
                            val entity = DataMapper.map(responseData)
                            val film = DataMapper.map(entity)
                            listEntities.add(entity)
                            listFilm.add(film)
                        }
                        tvShowDao.insertTvShows(*listEntities.toTypedArray())
                        if (showRemote) {
                            resource.call(responseHandler.handleSuccess(listFilm))
                        }
                    }
                } else {
                    resource.call(responseHandler.handleResponseCode(response.code()))
                }
            } catch (e: Exception) {
                resource.call(responseHandler.handleException(e))
            }
        }
    }
}