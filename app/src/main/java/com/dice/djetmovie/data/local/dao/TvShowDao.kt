package com.dice.djetmovie.data.local.dao

import androidx.room.*
import com.dice.djetmovie.data.local.entities.TvShowEntity

@Dao
interface TvShowDao {
    @Query("SELECT * FROM tb_tv_show ORDER BY popularity DESC")
    suspend fun getTvShows(): List<TvShowEntity>?

    @Query("SELECT * FROM tb_tv_show WHERE id = :id")
    suspend fun getTvShow(id: Int): TvShowEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(vararg movies: TvShowEntity)

    @Delete
    suspend fun deleteTvShow(movie: TvShowEntity)
}
