
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
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val listMovies = FilmDummy.generateMovies()
    private val listTvShows = FilmDummy.generateTvShows()

    @Test
    fun loadMovies() {
        with(onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE))))) {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listMovies.size - 1))
        }
    }

    @Test
    fun loadDetailMovie() {
        onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE)))).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        with(onView(withId(R.id.tv_title))) {
            check(matches(isDisplayed()))
            check(matches(withText(listMovies[0].title)))
        }
        with(onView(withId(R.id.tv_date_release))) {
            check(matches(isDisplayed()))
            check(matches(withText(listMovies[0].releaseDate)))
        }
        with(onView(withId(R.id.tv_overview))) {
            check(matches(isDisplayed()))
            check(matches(withText(listMovies[0].overview)))
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
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listTvShows.size - 1))
        }
    }

    @Test
    fun loadDetailTvMovies() {
        onView(withText("TV SHOW")).perform(click())
        with(onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW))))) {
            check(matches(isDisplayed()))
        }
        onView(
            allOf(
                withId(R.id.rv_films),
                withTagValue(`is`(Constants.FILM_TYPE_TV_SHOW))
            )
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        with(onView(withId(R.id.tv_title))) {
            check(matches(isDisplayed()))
            check(matches(withText(listTvShows[0].title)))
        }
        with(onView(withId(R.id.tv_date_release))) {
            check(matches(isDisplayed()))
            check(matches(withText(listTvShows[0].releaseDate)))
        }
        with(onView(withId(R.id.tv_overview))) {
            check(matches(isDisplayed()))
            check(matches(withText(listTvShows[0].overview)))
        }
        with(onView(withId(R.id.menu_share))) {
            check(matches(isDisplayed()))
        }
    }

}