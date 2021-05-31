package com.dice.djetmovie.data

import com.dice.djetmovie.data.local.entities.FavoriteMovieEntity
import com.dice.djetmovie.data.local.entities.FavoriteTvShowEntity
import com.dice.djetmovie.data.local.entities.MovieEntity
import com.dice.djetmovie.data.local.entities.TvShowEntity
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.response.ResponseMovie
import com.dice.djetmovie.data.remote.response.ResponseTvShow

@Suppress("Unused")
object DataMapper {

    fun map(response: ResponseMovie): MovieEntity {
        return with(response) {
            MovieEntity(
                id,
                title,
                posterPath,
                backdropPath,
                releaseDate,
                overview,
                popularity ?: 0F
            )
        }
    }

    fun map(response: ResponseTvShow): TvShowEntity {
        return with(response) {
            TvShowEntity(
                id,
                title,
                posterPath,
                backdropPath,
                releaseDate,
                overview,
                popularity ?: 0F
            )
        }
    }

    fun map(entity: MovieEntity): Film {
        return with(entity) {
            Film(
                id,
                Film.TYPE.MOVIE,
                title,
                posterPath ?: "",
                backdropPath ?: "",
                releaseDate ?: "",
                overview ?: ""
            )
        }
    }

    fun map(entity: FavoriteMovieEntity): Film {
        return with(entity) {
            Film(
                id,
                Film.TYPE.MOVIE,
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
                Film.TYPE.TV_SHOW,
                title,
                posterPath ?: "",
                backdropPath ?: "",
                releaseDate ?: "",
                overview ?: ""
            )
        }
    }

    fun map(entity: TvShowEntity): Film {
        return with(entity) {
            Film(
                id,
                Film.TYPE.TV_SHOW,
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
                Film.TYPE.MOVIE,
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
                Film.TYPE.TV_SHOW,
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