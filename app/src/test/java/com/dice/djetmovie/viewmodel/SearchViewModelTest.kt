package com.dice.djetmovie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.dice.djetmovie.CoroutineTestRule
import com.dice.djetmovie.data.repository.DataRepository
import com.dice.djetmovie.ui.search.SearchViewModel
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
class SearchViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    private lateinit var repo: DataRepository

    private lateinit var viewModel: SearchViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = SearchViewModel(repo)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    private val query = "alita"

    @Test
    fun testGetMoviesPaging() = coroutinesTestRule.testDispatcher.runBlockingTest {
        coEvery { repo.getMoviesPaging(any()) } returns flowOf(PagingData.from(DataDummy.movieFilm()))

        val result = viewModel.getMoviesPaging(query).first()

        Assert.assertNotNull(result)
    }

    @Test
    fun testGetTvShowPaging() = coroutinesTestRule.testDispatcher.runBlockingTest {
        coEvery { repo.getTvShowPaging(any()) } returns flowOf(PagingData.from(DataDummy.tvShowFilm()))

        val result = viewModel.getTvShowPaging(query).first()

        Assert.assertNotNull(result)
    }
}