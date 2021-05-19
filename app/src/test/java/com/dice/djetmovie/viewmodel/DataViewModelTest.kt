package com.dice.djetmovie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dice.djetmovie.data.DataMapper
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.response.ResponseMovieList
import com.dice.djetmovie.data.remote.response.ResponseTvShowList
import com.dice.djetmovie.data.remote.utils.Resource
import com.dice.djetmovie.data.repository.DataRepository
import com.dice.djetmovie.utils.NetworkHelper
import com.dice.djetmovie.utils.Utils.getJson
import com.dice.djetmovie.utils.getOrAwaitValue
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DataViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repo: DataRepository

    @MockK
    private lateinit var networkHelper: NetworkHelper

    private lateinit var dataViewModel: DataViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        dataViewModel = DataViewModel(repo, networkHelper)
    }

    @Test
    fun testGetMovies() {
        //untuk testing ambil data dari 'remote' atau 'local'
        val withRemote = true

        val response = Gson().fromJson(
            getJson("discover_movie_response.json"),
            ResponseMovieList::class.java
        )

        val listFilm = mutableListOf<Film>()
        for (itemResponse in response.results) {
            listFilm.add(DataMapper.map(DataMapper.map(itemResponse)))
        }
        val resource = Resource.success(listFilm)

        coEvery { networkHelper.isNetworkConnected() } returns withRemote
        coEvery { repo.getMoviesRemote() } returns resource
        coEvery { repo.getMoviesLocal() } returns resource

        dataViewModel.getMovies(true)

        val result = dataViewModel.listMovie.getOrAwaitValue()

        Assert.assertNotNull(result)
        Assert.assertEquals(listFilm, result)
    }

    @Test
    fun testGetTvShows() {
        //untuk testing ambil data dari 'remote' atau 'local'
        val withRemote = true

        val response = Gson().fromJson(
            getJson("discover_tv_response.json"),
            ResponseTvShowList::class.java
        )

        val listFilm = mutableListOf<Film>()
        for (itemResponse in response.results) {
            listFilm.add(DataMapper.map(DataMapper.map(itemResponse)))
        }
        val resource = Resource.success(listFilm)

        coEvery { networkHelper.isNetworkConnected() } returns withRemote
        coEvery { repo.getTvShowsRemote() } returns resource
        coEvery { repo.getTvShowsLocal() } returns resource

        dataViewModel.getTvShows(true)

        val result = dataViewModel.listTvShow.getOrAwaitValue()

        Assert.assertNotNull(result)
        Assert.assertEquals(listFilm, result)
    }
}