package com.dice.djetmovie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dice.djetmovie.CoroutineTestRule
import com.dice.djetmovie.data.repository.DataRepository
import com.dice.djetmovie.ui.detail.DetailViewModel
import com.dice.djetmovie.util.DataDummy
import com.dice.djetmovie.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    private lateinit var repo: DataRepository

    private lateinit var viewModel: DetailViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = DetailViewModel(repo)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun addFavoriteMovie() {
        val dummyFavored = DataDummy.dummyMovieFilm()[0]

        coEvery { repo.getFavoriteMovieById(any()) } returns dummyFavored

        viewModel.addMovieFavorite(dummyFavored)

        val isFavorite = viewModel.isFavorite.getOrAwaitValue()

        Assert.assertNotNull(isFavorite)
        Assert.assertEquals(true, isFavorite)
    }

    @Test
    fun removeFavoriteMovie() {
        val dummyFavored = DataDummy.dummyMovieFilm()[0]

        coEvery { repo.getFavoriteMovieById(any()) } returns dummyFavored

        viewModel.removeMovieFavorite(dummyFavored)

        val isFavorite = viewModel.isFavorite.getOrAwaitValue()

        Assert.assertNotNull(isFavorite)
        Assert.assertEquals(true, isFavorite)
    }

    @Test
    fun addFavoriteTvShow() {
        val dummyFavored = DataDummy.dummyTvShowFilm()[0]

        coEvery { repo.getFavoriteTvShowById(any()) } returns dummyFavored

        viewModel.addTvShowFavorite(dummyFavored)

        val isFavorite = viewModel.isFavorite.getOrAwaitValue()

        Assert.assertNotNull(isFavorite)
        Assert.assertEquals(true, isFavorite)
    }

    @Test
    fun removeFavoriteTvShow() {
        val dummyFavored = DataDummy.dummyTvShowFilm()[0]

        coEvery { repo.getFavoriteTvShowById(any()) } returns dummyFavored

        viewModel.checkIsFavorite(dummyFavored)

        val isFavorite = viewModel.isFavorite.getOrAwaitValue()

        Assert.assertNotNull(isFavorite)
        Assert.assertEquals(true, isFavorite)
    }

    @Test
    fun checkIsFavored() {
        //ubah untuk check favored Movie atau Tv Show
        val dummyFavored = DataDummy.dummyMovieFilm()[0]        //Movie
        //val dummyFavored = DataDummy.dummyTvShowFilm()[0]     //Tv Show

        coEvery { repo.getFavoriteTvShowById(any()) } returns dummyFavored
        coEvery { repo.getFavoriteMovieById(any()) } returns dummyFavored

        viewModel.removeTvShowFavorite(dummyFavored)

        val isFavorite = viewModel.isFavorite.getOrAwaitValue()

        Assert.assertNotNull(isFavorite)
        Assert.assertEquals(true, isFavorite)
    }
}