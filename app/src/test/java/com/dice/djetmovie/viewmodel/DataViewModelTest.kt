package com.dice.djetmovie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dice.djetmovie.data.Constants
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DataViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataViewModel: DataViewModel

    @Before
    fun setUp() {
        dataViewModel = DataViewModel()
    }

    @Test
    fun testGetMovies() {
        dataViewModel.getFilms(Constants.FILM_TYPE_MOVIE)
        dataViewModel.listFilms.observeForever { result ->
            Assert.assertNotNull(result)
            Assert.assertEquals(FilmDummy.generateMovies(), result)
        }
    }

    @Test
    fun testGetTvShows() {
        dataViewModel.getFilms(Constants.FILM_TYPE_TV_SHOW)
        dataViewModel.listFilms.observeForever { result ->
            Assert.assertNotNull(result)
            Assert.assertEquals(FilmDummy.generateTvShows(), result)
        }
    }
}