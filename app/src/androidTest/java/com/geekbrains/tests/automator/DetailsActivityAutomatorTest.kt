package com.geekbrains.tests.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class DetailsActivityAutomatorTest {

    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val packageName = context.packageName

    @Before
    fun setUp() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)

        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))

        toDetails.click()
    }

    companion object {
        private const val TIMEOUT = 50000L
    }

    @Test
    fun test_decrementButtonNotNUll() {

        val decrementButton =
            uiDevice.wait(Until.findObject(By.res(packageName, "decrementButton")), TIMEOUT)

        assertNotNull(decrementButton)
    }

    @Test
    fun test_incrementButtonNotNUll() {

        val incrementButton =
            uiDevice.wait(Until.findObject(By.res(packageName, "incrementButton")), TIMEOUT)

        assertNotNull(incrementButton)
    }

    @Test
    fun test_decrementButtonIsWork() {

        val decrementButton =
            uiDevice.wait(Until.findObject(By.res(packageName, "decrementButton")), TIMEOUT)
        decrementButton.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )

        assertEquals(changedText.text, "Number of results: -1")
    }

    @Test
    fun test_incrementButtonIsWork() {

        val decrementButton =
            uiDevice.wait(Until.findObject(By.res(packageName, "incrementButton")), TIMEOUT)
        decrementButton.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )

        assertEquals(changedText.text, "Number of results: 1")
    }

    @Test
    fun test_incrementButtonIsWork2() {

        val decrementButton =
            uiDevice.wait(Until.findObject(By.res(packageName, "incrementButton")), TIMEOUT)
        decrementButton.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )

        assertNotEquals(changedText.text, "Number of results: 0")
    }

}