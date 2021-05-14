
package com.dice.djetmovie.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dice.djetmovie.R
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.data.repository.DataRepository
import com.dice.djetmovie.viewmodel.DataViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest


class MainActivityTest : KoinTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @MockK
    private lateinit var repo: DataRepository

    private lateinit var dataViewModel: DataViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        dataViewModel = DataViewModel(repo)
    }

    private fun waitDelay(delay: Long = 2000) {
        try {
            Thread.sleep(delay)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    @Test
    fun loadMovies() {
        with(onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE))))) {
            check(matches(isDisplayed()))

            waitDelay()

            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
        }
    }

    @Test
    fun loadDetailMovie() {
        waitDelay()

        onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE)))).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        with(onView(withId(R.id.tv_title))) {
            check(matches(isDisplayed()))
        }
        with(onView(withId(R.id.tv_date_release))) {
            check(matches(isDisplayed()))
        }
        with(onView(withId(R.id.tv_overview))) {
            check(matches(isDisplayed()))
        }
        with(onView(withId(R.id.menu_share))) {
            check(matches(isDisplayed()))
        }
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV SHOW")).perform(click())
        with(onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW))))) {
            check(matches(isDisplayed()))

            waitDelay()

            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
        }
    }

    @Test
    fun loadDetailTvMovies() {
        waitDelay()

        onView(withText("TV SHOW")).perform(click())
        with(onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW))))) {
            check(matches(isDisplayed()))
        }
        onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW)))
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        with(onView(withId(R.id.tv_title))) {
            check(matches(isDisplayed()))
        }
        with(onView(withId(R.id.tv_date_release))) {
            check(matches(isDisplayed()))
        }
        with(onView(withId(R.id.tv_overview))) {
            check(matches(isDisplayed()))
        }
        with(onView(withId(R.id.menu_share))) {
            check(matches(isDisplayed()))
        }
    }

}