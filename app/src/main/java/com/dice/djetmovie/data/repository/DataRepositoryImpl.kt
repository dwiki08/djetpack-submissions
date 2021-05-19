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

class DataRepositoryImpl(
    private val responseHandler: ResponseHandler,
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao
) : DataRepository {

    private val apiKey = Constants.API_KEY
    private val apiLanguage = Constants.API_LANGUAGE
    private val msgNoInternet = "Failed to getting data from server."

    override suspend fun getMoviesRemote(): Resource<List<Film>> {
        val listEntities = mutableListOf<MovieEntity>()
        val listFilm = mutableListOf<Film>()

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
                    movieDao.insertMovies(*listEntities.toTypedArray())
                    return Resource.success(listFilm)
                }
            } else {
                return responseHandler.handleResponseCode(response.code(), listFilm)
            }
        } catch (e: Exception) {
            return responseHandler.handleException(e, listFilm)
        }

        return Resource.error(msgNoInternet, listFilm)
    }

    override suspend fun getMoviesLocal(): Resource<List<Film>> {
        val listEntities = movieDao.getMovies() as MutableList
        val listFilm = DataMapper.mapListMovie(listEntities) as MutableList
        return Resource.success(listFilm)
    }

    override suspend fun getTvShowsRemote(): Resource<List<Film>> {
        val listEntities = mutableListOf<TvShowEntity>()
        val listFilm = mutableListOf<Film>()

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
                    tvShowDao.insertTvShows(*listEntities.toTypedArray())
                    return Resource.success(listFilm)
                }
            } else {
                return responseHandler.handleResponseCode(response.code(), listFilm)
            }
        } catch (e: Exception) {
            return responseHandler.handleException(e, listFilm)
        }

        return Resource.error(msgNoInternet, listFilm)
    }

    override suspend fun getTvShowsLocal(): Resource<List<Film>> {
        val listEntities = tvShowDao.getTvShows() as MutableList
        val listFilm = DataMapper.mapListTvShow(listEntities) as MutableList
        return Resource.success(listFilm)
    }
}