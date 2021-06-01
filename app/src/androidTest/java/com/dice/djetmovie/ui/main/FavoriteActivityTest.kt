package com.dice.djetmovie.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dice.djetmovie.R
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.util.RecyclerViewItemCountAssertion
import com.dice.djetmovie.utils.EspressoIdlingResource
import io.mockk.MockKAnnotations
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class FavoriteActivityTest : KoinTest {

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
    fun emptyMovieFavorite() {
        val rvMovie = allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE)))

        onView(withId(R.id.menu_favorites)).perform(click())
        onView(rvMovie).check(RecyclerViewItemCountAssertion(0))
        onView(allOf(withId(R.id.view_empty), withTagValue(`is`(Constants.FILM_TYPE_MOVIE))))
                .check(matches(isDisplayed()))
    }

    @Test
    fun emptyTvShowFavorite() {
        onView(withId(R.id.menu_favorites)).perform(click())
        onView(withText("TV SHOW")).perform(click())
        onView(allOf(withId(R.id.view_empty), withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW))))
                .check(matches(isDisplayed()))
    }


    @Test
    fun addAndRemoveMovieFavorite() {
        val rvMovie = allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE)))

        onView(rvMovie).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //add favorite
        onView(withId(R.id.menu_add_favorite)).perform(click())

        //check favorite
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.menu_favorites)).perform(click())
        with(onView(rvMovie)) {
            check(RecyclerViewItemCountAssertion(1))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }
        checkDetailMovie()

        //remove favorite
        onView(withId(R.id.menu_add_favorite)).perform(click())

        //check is empty
        onView(isRoot()).perform(pressBack())
        onView(rvMovie).check(RecyclerViewItemCountAssertion(0))
        onView(allOf(withId(R.id.view_empty), withTagValue(`is`(Constants.FILM_TYPE_MOVIE))))
                .check(matches(isDisplayed()))
    }


    @Test
    fun addAndRemoveTvShowFavorite() {
        val rvTvShow = Matchers.allOf(withId(R.id.rv_films), withTagValue(Matchers.`is`(Constants.FILM_TYPE_TV_SHOW)))

        onView(withText("TV SHOW")).perform(click())
        with(onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW))))) {
            check(matches(isDisplayed()))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }

        //add favorite
        onView(withId(R.id.menu_add_favorite)).perform(click())

        //check favorite
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.menu_favorites)).perform(click())
        onView(withText("TV SHOW")).perform(click())
        with(onView(rvTvShow)) {
            check(RecyclerViewItemCountAssertion(1))
            perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }
        checkDetailTvShow()

        //remove favorite
        onView(withId(R.id.menu_add_favorite)).perform(click())

        //check is empty
        onView(isRoot()).perform(pressBack())
        onView(rvTvShow).check(RecyclerViewItemCountAssertion(0))
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
}