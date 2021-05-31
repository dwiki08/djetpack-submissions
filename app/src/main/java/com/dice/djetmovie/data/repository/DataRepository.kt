package com.dice.djetmovie.data.repository

import androidx.paging.PagingData
import com.dice.djetmovie.data.model.Film
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getMoviesPaging(query: String? = null): Flow<PagingData<Film>>

    fun getTvShowPaging(query: String? = null): Flow<PagingData<Film>>

    fun getFavoriteMovies(): Flow<PagingData<Film>>

    fun getFavoriteTvShow(): Flow<PagingData<Film>>

    suspend fun addFavoriteMovie(film: Film)

    suspend fun addFavoriteTvShow(film: Film)

    suspend fun removeFavoriteMovie(film: Film)

    suspend fun removeFavoriteTvShow(film: Film)

    suspend fun getFavoriteMovieById(id: Int): Film?

    suspend fun getFavoriteTvShowById(id: Int): Film?
}