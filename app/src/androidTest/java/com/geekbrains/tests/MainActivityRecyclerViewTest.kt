package com.geekbrains.tests

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.geekbrains.tests.view.search.MainActivity
import com.geekbrains.tests.view.search.SearchResultAdapter.SearchResultViewHolder
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test


class MainActivityRecyclerViewTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        val searchEditText = onView(withId(R.id.searchEditText))

        searchEditText.perform(click())
        searchEditText.perform(replaceText("algol"), closeSoftKeyboard())
        searchEditText.perform(pressImeActionButton())
    }

    @Test
    fun activitySearch_ScrollTo() {
        if (BuildConfig.IS_TEST) {
            onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.scrollTo<SearchResultViewHolder>(
                    hasDescendant(withText("FullName: 42"))
                )
            )

        }
    }

    @Test
    fun activitySearch_PerformClickAtPosition() {
        if (BuildConfig.IS_TEST) {
            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<SearchResultViewHolder>(
                        0,
                        click()
                    )
                )
        }
    }

    @Test
    fun activitySearch_PerformClickOnItem() {
        if (BuildConfig.IS_TEST) {
            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions.scrollTo<SearchResultViewHolder>(
                        hasDescendant(withText("FullName: 50"))
                    )
                )

            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions.actionOnItem<SearchResultViewHolder>(
                        hasDescendant(withText("FullName: 42")),
                        click()
                    )
                )
        }
    }

    @Test
    fun activitySearch_PerformCustomClick() {
        if (BuildConfig.IS_TEST) {
            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions
                        .actionOnItemAtPosition<SearchResultViewHolder>(
                            0,
                            tapOnItemWithId(R.id.checkbox)
                        )
                )
        }
    }

    private fun tapOnItemWithId(id: Int) = object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Нажимаем на view с указанным id"
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById(id) as View
            v.performClick()
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}