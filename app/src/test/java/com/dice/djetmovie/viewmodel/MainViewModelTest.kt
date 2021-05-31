package com.dice.djetmovie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.dice.djetmovie.CoroutineTestRule
import com.dice.djetmovie.data.repository.DataRepository
import com.dice.djetmovie.ui.main.MainViewModel
import com.dice.djetmovie.util.DataDummy
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    private lateinit var repo: DataRepository

    private lateinit var viewModel: MainViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MainViewModel(repo)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testGetMoviesPaging() = coroutinesTestRule.testDispatcher.runBlockingTest {
        coEvery { repo.getMoviesPaging() } returns flowOf(PagingData.from(DataDummy.dummyMovieFilm()))

        val result = viewModel.getMoviesPaging().first()

        Assert.assertNotNull(result)
    }

    @Test
    fun testGetTvShowPaging() = coroutinesTestRule.testDispatcher.runBlockingTest {
        coEvery { repo.getTvShowPaging() } returns flowOf(PagingData.from(DataDummy.dummyTvShowFilm()))

        val result = viewModel.getTvShowPaging().first()

        Assert.assertNotNull(result)
    }
}