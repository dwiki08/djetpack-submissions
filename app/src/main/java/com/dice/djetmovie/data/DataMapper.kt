package com.dice.djetmovie.data

import com.dice.djetmovie.data.local.entities.MovieEntity
import com.dice.djetmovie.data.local.entities.TvShowEntity
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.response.ResponseMovie
import com.dice.djetmovie.data.remote.response.ResponseTvShow

object DataMapper {

    fun map(movie: ResponseMovie): MovieEntity {
        return with(movie) {
            MovieEntity(id, title, posterPath, backdropPath, releaseDate, overview, popularity)
        }
    }

    fun map(tvShow: ResponseTvShow): TvShowEntity {
        return with(tvShow) {
            TvShowEntity(id, title, posterPath, backdropPath, releaseDate, overview, popularity)
        }
    }

    fun map(movie: MovieEntity): Film {
        return with(movie) {
            Film(
                    id,
                    title,
                    posterPath ?: "",
                    backdropPath ?: "",
                    releaseDate ?: "",
                    overview ?: ""
            )
        }
    }

    fun map(tvShow: TvShowEntity): Film {
        return with(tvShow) {
            Film(
                    id,
                    title,
                    posterPath ?: "",
                    backdropPath ?: "",
                    releaseDate ?: "",
                    overview ?: ""
            )
        }
    }
}