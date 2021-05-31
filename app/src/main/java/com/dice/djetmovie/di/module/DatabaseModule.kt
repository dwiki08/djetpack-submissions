package com.dice.djetmovie.di.module

import androidx.room.Room
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, Constants.DB_FILM)
            .fallbackToDestructiveMigration().build()
    }

    factory { get<AppDatabase>().movieDao() }
    factory { get<AppDatabase>().tvShowDao() }
    factory { get<AppDatabase>().favoriteMovieDao() }
    factory { get<AppDatabase>().favoriteTvShowDao() }
}