package com.dice.djetmovie.data

import com.dice.djetmovie.data.local.entities.MovieEntity
import com.dice.djetmovie.data.local.entities.TvShowEntity
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.response.ResponseMovie
import com.dice.djetmovie.data.remote.response.ResponseTvShow

object DataMapper {

    fun map(response: ResponseMovie): MovieEntity {
        return with(response) {
            MovieEntity(id, title, posterPath, backdropPath, releaseDate, overview, popularity)
        }
    }

    fun map(response: ResponseTvShow): TvShowEntity {
        return with(response) {
            TvShowEntity(id, title, posterPath, backdropPath, releaseDate, overview, popularity)
        }
    }

    fun map(entity: MovieEntity): Film {
        return with(entity) {
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

    fun map(entity: TvShowEntity): Film {
        return with(entity) {
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

    fun mapListMovie(listEntities: List<MovieEntity>): List<Film> {
        val listFilm = mutableListOf<Film>()
        for (entity in listEntities) {
            listFilm.add(with(entity) {
                Film(
                        id,
                        title,
                        posterPath ?: "",
                        backdropPath ?: "",
                        releaseDate ?: "",
                        overview ?: ""
                )
            })
        }
        return listFilm
    }

    fun mapListTvShow(listEntities: List<TvShowEntity>): List<Film> {
        val listFilm = mutableListOf<Film>()
        for (entity in listEntities) {
            listFilm.add(with(entity) {
                Film(
                        id,
                        title,
                        posterPath ?: "",
                        backdropPath ?: "",
                        releaseDate ?: "",
                        overview ?: ""
                )
            })
        }
        return listFilm
    }
}