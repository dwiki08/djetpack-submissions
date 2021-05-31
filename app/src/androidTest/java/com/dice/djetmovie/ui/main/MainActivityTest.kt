package com.dice.djetmovie.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dice.djetmovie.R
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.util.RecyclerViewItemCountAssertion
import com.dice.djetmovie.utils.EspressoIdlingResource
import io.mockk.MockKAnnotations
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest


class MainActivityTest : KoinTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        with(onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE))))) {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(39))
        }
    }

    private fun checkDetailMovie() {
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
    fun loadDetailMovie() {
        onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE)))).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        checkDetailMovie()
    }

    @Test
    fun addAndRemoveMovieFavorite() {
        loadDetailMovie()

        //add favorite
        onView(withId(R.id.menu_add_favorite)).perform(click())

        //check favorite
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.menu_favorites)).perform(click())
        onView(withId(R.id.rv_films)).check(RecyclerViewItemCountAssertion(1))
        onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE))))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        checkDetailMovie()

        //remove favorite
        onView(withId(R.id.menu_add_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.rv_films)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV SHOW")).perform(click())
        with(
            onView(
                allOf(
                    withId(R.id.rv_films),
                    withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW))
                )
            )
        ) {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(39))
        }
    }

    private fun checkDetailTvShow() {
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
    fun loadDetailTvShow() {
        onView(withText("TV SHOW")).perform(click())
        with(
            onView(
                allOf(
                    withId(R.id.rv_films),
                    withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW))
                )
            )
        ) {
            check(matches(isDisplayed()))
        }
        onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW))))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        checkDetailTvShow()
    }

    @Test
    fun addAndRemoveTvShowFavorite() {
        loadDetailTvShow()

        //add favorite
        onView(withId(R.id.menu_add_favorite)).perform(click())

        //check favorite
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.menu_favorites)).perform(click())
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_films)).check(RecyclerViewItemCountAssertion(1))
        onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW))))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        checkDetailTvShow()

        //remove favorite
        onView(withId(R.id.menu_add_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.rv_films)).check(RecyclerViewItemCountAssertion(0))
    }
}