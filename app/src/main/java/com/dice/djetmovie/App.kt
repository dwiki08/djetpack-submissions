package com.dice.djetmovie

import android.app.Application
import com.dice.djetmovie.di.module.databaseModule
import com.dice.djetmovie.di.module.networkModule
import com.dice.djetmovie.di.module.repoModule
import com.dice.djetmovie.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, repoModule, viewModelModule, databaseModule))
        }
    }
}