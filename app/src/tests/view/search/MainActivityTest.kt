package com.afrosin.apptests.view.search

import android.os.Build
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.afrosin.apptests.R
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun check_SearchEditText() {
        scenario.onActivity {
            val searchEditText = it.findViewById<EditText>(R.id.searchEditText)
            assertNotNull(searchEditText)

            searchEditText.setText("text", TextView.BufferType.EDITABLE)

            assertNotNull(searchEditText.text)
            assertEquals("text", searchEditText.text.toString())


        }
    }

    @After
    fun close() {
        scenario.close()
    }
}