package com.dice.djetmovie.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dice.djetmovie.R
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.utils.EspressoIdlingResource
import io.mockk.MockKAnnotations
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class SearchActivityTest : KoinTest {

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

    private val query = "pirates"

    @Test
    fun searchMovie() {
        onView(withId(R.id.menu_search)).perform(click())
        onView(withId(R.id.edt_query)).perform(typeText(query), pressImeActionButton())
        with(
            onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE))))
        ) {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        }
    }

    @Test
    fun searchTvShow() {
        onView(withId(R.id.menu_search)).perform(click())
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.edt_query)).perform(typeText(query), pressImeActionButton())
        with(
            onView(allOf(withId(R.id.rv_films), withTagValue(`is`(Constants.FILM_TYPE_MOVIE))))
        ) {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        }
    }
}