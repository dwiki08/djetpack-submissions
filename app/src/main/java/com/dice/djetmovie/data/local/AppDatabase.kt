package com.dice.djetmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dice.djetmovie.data.local.dao.FavoriteMovieDao
import com.dice.djetmovie.data.local.dao.FavoriteTvShowDao
import com.dice.djetmovie.data.local.dao.MovieDao
import com.dice.djetmovie.data.local.dao.TvShowDao
import com.dice.djetmovie.data.local.entities.FavoriteMovieEntity
import com.dice.djetmovie.data.local.entities.FavoriteTvShowEntity
import com.dice.djetmovie.data.local.entities.MovieEntity
import com.dice.djetmovie.data.local.entities.TvShowEntity

@Database(
    entities = [MovieEntity::class, TvShowEntity::class, FavoriteMovieEntity::class, FavoriteTvShowEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun favoriteTvShowDao(): FavoriteTvShowDao
}
