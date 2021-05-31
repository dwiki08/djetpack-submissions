package com.dice.djetmovie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.dice.djetmovie.data.DataMapper
import com.dice.djetmovie.data.local.dao.FavoriteMovieDao
import com.dice.djetmovie.data.local.dao.FavoriteTvShowDao
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.ApiService
import com.dice.djetmovie.data.remote.source.MoviePagingSource
import com.dice.djetmovie.data.remote.source.TvShowPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataRepositoryImpl(
    private val apiService: ApiService,
    private val favMovieDao: FavoriteMovieDao,
    private val favTvShowDao: FavoriteTvShowDao
) : DataRepository {

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    override fun getMoviesPaging(query: String?): Flow<PagingData<Film>> {
        return Pager(PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ), pagingSourceFactory = { MoviePagingSource(apiService, query) }
        ).flow
    }

    override fun getTvShowPaging(query: String?): Flow<PagingData<Film>> {
        return Pager(PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ), pagingSourceFactory = { TvShowPagingSource(apiService, query) }
        ).flow
    }

    override fun getFavoriteMovies(): Flow<PagingData<Film>> {
        return Pager(PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ), pagingSourceFactory = { favMovieDao.getMovies() }
        ).flow.map { it.map { entity -> DataMapper.map(entity) } }
    }

    override fun getFavoriteTvShow(): Flow<PagingData<Film>> {
        return Pager(PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ), pagingSourceFactory = { favTvShowDao.getTvShow() }
        ).flow.map { it.map { entity -> DataMapper.map(entity) } }
    }

    override suspend fun addFavoriteMovie(film: Film) {
        favMovieDao.insertMovies(*listOf(DataMapper.mapMovie(film)).toTypedArray())
    }

    override suspend fun addFavoriteTvShow(film: Film) {
        favTvShowDao.insertTvShows(*listOf(DataMapper.mapTvShow(film)).toTypedArray())
    }

    override suspend fun removeFavoriteMovie(film: Film) {
        favMovieDao.deleteMovie(DataMapper.mapMovie(film))
    }

    override suspend fun removeFavoriteTvShow(film: Film) {
        favTvShowDao.deleteTvShow(DataMapper.mapTvShow(film))
    }

    override suspend fun getFavoriteMovieById(id: Int): Film? {
        val entity = favMovieDao.getMovie(id)
        return if (entity == null) null else DataMapper.map(entity)
    }

    override suspend fun getFavoriteTvShowById(id: Int): Film? {
        val entity = favTvShowDao.getTvShow(id)
        return if (entity == null) null else DataMapper.map(entity)
    }
}