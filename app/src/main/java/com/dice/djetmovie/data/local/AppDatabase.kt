package com.dice.djetmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dice.djetmovie.data.local.dao.FavoriteMovieDao
import com.dice.djetmovie.data.local.dao.FavoriteTvShowDao
import com.dice.djetmovie.data.local.entities.FavoriteMovieEntity
import com.dice.djetmovie.data.local.entities.FavoriteTvShowEntity

@Database(
        entities = [FavoriteMovieEntity::class, FavoriteTvShowEntity::class],
        version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun favoriteTvShowDao(): FavoriteTvShowDao
}
