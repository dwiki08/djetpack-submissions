package com.dice.djetmovie.data

import com.dice.djetmovie.data.local.entities.FavoriteMovieEntity
import com.dice.djetmovie.data.local.entities.FavoriteTvShowEntity
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.response.ResponseMovie
import com.dice.djetmovie.data.remote.response.ResponseTvShow

object DataMapper {

    fun map(entity: FavoriteMovieEntity): Film {
        return with(entity) {
            Film(
                    id,
                    Constants.FILM_TYPE_MOVIE,
                    title,
                    posterPath ?: "",
                    backdropPath ?: "",
                    releaseDate ?: "",
                    overview ?: ""
            )
        }
    }

    fun map(entity: FavoriteTvShowEntity): Film {
        return with(entity) {
            Film(
                    id,
                    Constants.FILM_TYPE_TV_SHOW,
                    title,
                    posterPath ?: "",
                    backdropPath ?: "",
                    releaseDate ?: "",
                    overview ?: ""
            )
        }
    }

    fun mapMovie(response: ResponseMovie): Film {
        return with(response) {
            Film(
                    id,
                    Constants.FILM_TYPE_MOVIE,
                    title,
                    posterPath ?: "",
                    backdropPath ?: "",
                    releaseDate ?: "",
                    overview ?: ""
            )
        }
    }

    fun mapTvShow(response: ResponseTvShow): Film {
        return with(response) {
            Film(
                    id,
                    Constants.FILM_TYPE_TV_SHOW,
                    title,
                    posterPath ?: "",
                    backdropPath ?: "",
                    releaseDate ?: "",
                    overview ?: ""
            )
        }
    }

    fun mapMovie(film: Film): FavoriteMovieEntity {
        return with(film) {
            FavoriteMovieEntity(
                    id,
                    title,
                    posterPath,
                    backdropPath,
                    releaseDate,
                    overview
            )
        }
    }

    fun mapTvShow(film: Film): FavoriteTvShowEntity {
        return with(film) {
            FavoriteTvShowEntity(
                    id,
                    title,
                    posterPath,
                    backdropPath,
                    releaseDate,
                    overview
            )
        }
    }
}