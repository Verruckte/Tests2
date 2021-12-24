package com.geekbrains.tests.presenter.details

import com.geekbrains.tests.view.details.ViewDetailsContract
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewDetailsContract: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter(viewDetailsContract)
    }

    @Test
    fun onCounterInitialValue_AssertEquals() {
        assertEquals(0, presenter.getCounter())
    }

    @Test
    fun onSetCounter_AssertEquals() {
        presenter.setCounter(10)
        assertEquals(10, presenter.getCounter())
    }

    @Test
    fun onDecrement_AssertEquals() {
        presenter.onDecrement()
        assertEquals(-1, presenter.getCounter())
    }

    @Test
    fun onIncrement_AssertEquals() {
        presenter.onIncrement()
        assertEquals(1, presenter.getCounter())
    }

    @Test
    fun onAttach_AssertNotNull() {
        presenter.onAttach(viewDetailsContract)
        assertNotNull(presenter.viewDetailsContract)
    }

    @Test
    fun onDetach_AssertNUll() {
        presenter.onDetach()
        assertNull(presenter.viewDetailsContract)
    }
}