package com.geekbrains.tests

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.viewbinding.BuildConfig
import com.geekbrains.tests.view.search.MainActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    private fun delay(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $10 seconds"

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(10000)
            }
        }
    }


    @Test
    fun activitySearch_IsWorking() {

        val searchEditText = onView(withId(R.id.searchEditText))
        searchEditText.perform(click())
        searchEditText.perform(replaceText("algol"), closeSoftKeyboard())
        searchEditText.perform(pressImeActionButton())

        if (BuildConfig.IS_TEST) {
            onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 42")))
        } else {
            onView(isRoot()).perform(delay())

            onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 2655")))
        }

    }

    @Test
    fun toDetailsActivityButton_IsVisible() {
        onView(withId(R.id.toDetailsActivityButton)).check(
            matches(
                withEffectiveVisibility(
                    Visibility.VISIBLE
                )
            )
        )
    }

    @Test
    fun searchEditTextHint_IsEqual() {
        onView(withId(R.id.searchEditText)).check(
            matches(
                withHint("Enter keyword e.g. android")
            )
        )
    }

    @Test
    fun progressBar_IsVisibleGone() {
        onView(withId(R.id.progressBar)).check(
            matches(
                withEffectiveVisibility(Visibility.GONE)
            )
        )
    }

    @After
    fun close() {
        scenario.close()
    }
}