package com.dice.djetmovie.di.module

import com.dice.djetmovie.viewmodel.DataViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DataViewModel(get(), get()) }
}