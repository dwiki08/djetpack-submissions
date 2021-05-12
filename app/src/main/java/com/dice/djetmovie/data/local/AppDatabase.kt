package com.dice.djetmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dice.djetmovie.data.local.dao.MovieDao
import com.dice.djetmovie.data.local.dao.TvShowDao
import com.dice.djetmovie.data.local.entities.MovieEntity
import com.dice.djetmovie.data.local.entities.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
}
