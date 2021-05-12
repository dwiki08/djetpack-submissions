package com.dice.djetmovie.data.local.dao

import androidx.room.*
import com.dice.djetmovie.data.local.entities.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM tb_movie ORDER BY popularity DESC")
    suspend fun getMovies(): List<MovieEntity>?

    @Query("SELECT * FROM tb_movie WHERE id = :id")
    suspend fun getMovie(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movies: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
}
