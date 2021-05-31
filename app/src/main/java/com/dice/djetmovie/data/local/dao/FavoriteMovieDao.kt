package com.dice.djetmovie.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.dice.djetmovie.data.local.entities.FavoriteMovieEntity

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM tb_fav_movie")
    fun getMovies(): PagingSource<Int, FavoriteMovieEntity>

    @Query("SELECT * FROM tb_fav_movie WHERE id = :id")
    suspend fun getMovie(id: Int): FavoriteMovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg entity: FavoriteMovieEntity)

    @Delete
    suspend fun deleteMovie(entity: FavoriteMovieEntity)

    @Query("DELETE FROM tb_fav_movie")
    suspend fun clearMovie()
}