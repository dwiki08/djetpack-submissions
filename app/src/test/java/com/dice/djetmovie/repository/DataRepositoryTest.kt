package com.dice.djetmovie.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dice.djetmovie.CoroutineTestRule
import com.dice.djetmovie.data.DataMapper
import com.dice.djetmovie.data.local.dao.MovieDao
import com.dice.djetmovie.data.local.dao.TvShowDao
import com.dice.djetmovie.data.local.entities.MovieEntity
import com.dice.djetmovie.data.local.entities.TvShowEntity
import com.dice.djetmovie.data.remote.ApiService
import com.dice.djetmovie.data.remote.response.ResponseMovieList
import com.dice.djetmovie.data.remote.response.ResponseTvShowList
import com.dice.djetmovie.data.remote.utils.Resource
import com.dice.djetmovie.data.remote.utils.ResponseHandler
import com.dice.djetmovie.data.repository.DataRepositoryImpl
import com.dice.djetmovie.utils.Utils.getJson
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.koin.test.KoinTest
import retrofit2.Response

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class DataRepositoryTest : KoinTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    private lateinit var responseHandler: ResponseHandler

    @MockK
    private lateinit var apiService: ApiService

    @MockK
    private lateinit var movieDao: MovieDao

    @MockK
    private lateinit var tvShowDao: TvShowDao

    private lateinit var repo: DataRepositoryImpl

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repo = DataRepositoryImpl(responseHandler, apiService, movieDao, tvShowDao)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getMoviesRemote() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val response = Gson().fromJson(
            getJson("discover_movie_response.json"),
            ResponseMovieList::class.java
        )

        coEvery { apiService.getMovie(any(), any(), any()) } returns Response.success(response)

        val result = repo.getMoviesRemote()
        Assert.assertEquals(Resource.Status.SUCCESS, result.status)
    }

    @Test
    fun getTvShowRemote() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val response = Gson().fromJson(
            getJson("discover_tv_response.json"),
            ResponseTvShowList::class.java
        )

        coEvery { apiService.getTvShow(any(), any(), any()) } returns Response.success(response)

        val result = repo.getTvShowsRemote()
        Assert.assertEquals(Resource.Status.SUCCESS, result.status)
    }

    @Test
    fun getMoviesLocal() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val response = Gson().fromJson(
            getJson("discover_movie_response.json"),
            ResponseMovieList::class.java
        )
        val listEntity = mutableListOf<MovieEntity>()
        for (itemResponse in response.results) {
            listEntity.add(DataMapper.map(itemResponse))
        }

        coEvery { movieDao.getMovies() } returns listEntity

        val result = repo.getMoviesLocal()
        Assert.assertEquals(Resource.Status.SUCCESS, result.status)
    }

    @Test
    fun getTvShowLocal() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val response = Gson().fromJson(
            getJson("discover_tv_response.json"),
            ResponseTvShowList::class.java
        )
        val listEntity = mutableListOf<TvShowEntity>()
        for (itemResponse in response.results) {
            listEntity.add(DataMapper.map(itemResponse))
        }

        coEvery { tvShowDao.getTvShows() } returns listEntity

        val result = repo.getTvShowsLocal()
        Assert.assertEquals(Resource.Status.SUCCESS, result.status)
    }

}