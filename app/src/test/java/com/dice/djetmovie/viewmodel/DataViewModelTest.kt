package com.dice.djetmovie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dice.djetmovie.data.remote.utils.Resource
import com.dice.djetmovie.data.repository.DataRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class DataViewModelTest : KoinTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repo: DataRepository

    private lateinit var dataViewModel: DataViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        dataViewModel = DataViewModel(repo)
    }

    @Test
    fun testGetMovies() {
        dataViewModel.getMovies()
        dataViewModel.listMovie.observeForever { result ->
            Assert.assertNotNull(result)
            when (result?.status) {
                Resource.Status.SUCCESS -> {
                    Assert.assertEquals(20, result.data?.size)
                }
                Resource.Status.ERROR -> println("Error")
            }
        }
    }

    @Test
    fun testGetTvShows() {
        dataViewModel.getTvShows()
        dataViewModel.listMovie.observeForever { result ->
            Assert.assertNotNull(result)
            when (result?.status) {
                Resource.Status.SUCCESS -> {
                    Assert.assertEquals(20, result.data?.size)
                }
                Resource.Status.ERROR -> println("Error")
            }
        }
    }
}