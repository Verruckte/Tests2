package com.geekbrains.tests

import android.content.Context
import android.os.Build
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.presenter.search.SearchPresenter
import com.geekbrains.tests.view.search.MainActivity
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun searchPresenter_onAttach() {
        scenario.onActivity {
            scenario.moveToState(Lifecycle.State.CREATED)
            val view = getViewFromPresenter(it)
            TestCase.assertEquals(false, view == null)
        }
    }

    private fun getViewFromPresenter(activity: MainActivity): View? {
        val field1 = MainActivity::class.java.getDeclaredField("presenter")
        field1.isAccessible = true
        val presenter = field1.get(activity) as SearchPresenter
        val field2 = SearchPresenter::class.java.getDeclaredField("view")
        field2.isAccessible = true
        return field2.get(presenter) as View?
    }

    @Test
    fun searchPresenter_onDetach() {
        scenario.onActivity {
            scenario.moveToState(Lifecycle.State.DESTROYED)
            val view = getViewFromPresenter(it)
            TestCase.assertEquals(true, view == null)
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}