package com.dice.djetmovie.di.module

import com.dice.djetmovie.ui.detail.DetailViewModel
import com.dice.djetmovie.ui.favorite.FavoriteViewModel
import com.dice.djetmovie.ui.main.MainViewModel
import com.dice.djetmovie.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}