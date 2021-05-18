package com.dice.djetmovie.di.module

import com.dice.djetmovie.data.repository.DataRepository
import com.dice.djetmovie.data.repository.DataRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repoModule = module {
    factory<DataRepository> { DataRepositoryImpl(androidContext(), get(), get(), get(), get()) }
}