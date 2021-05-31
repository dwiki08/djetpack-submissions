package com.dice.djetmovie.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.dice.djetmovie.data.local.entities.FavoriteTvShowEntity

@Dao
interface FavoriteTvShowDao {
    @Query("SELECT * FROM tb_fav_tv_show")
    fun getTvShow(): PagingSource<Int, FavoriteTvShowEntity>

    @Query("SELECT * FROM tb_fav_tv_show WHERE id = :id")
    suspend fun getTvShow(id: Int): FavoriteTvShowEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(vararg entity: FavoriteTvShowEntity)

    @Delete
    suspend fun deleteTvShow(entity: FavoriteTvShowEntity)

    @Query("DELETE FROM tb_fav_tv_show")
    suspend fun clearTvShow()
}