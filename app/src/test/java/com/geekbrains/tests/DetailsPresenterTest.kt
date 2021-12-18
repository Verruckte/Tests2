package com.geekbrains.tests

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {
    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this) //initMocks is Deprecated
        presenter = DetailsPresenter(viewContract)
    }

    @Test
    fun detailsPresenter_onDecrement() {
        presenter.setCounter(2)
        presenter.onDecrement()
        verify(viewContract).setCount(1)
    }

    @Test
    fun detailsPresenter_onIncrement() {
        presenter.setCounter(0)
        presenter.onIncrement()
        verify(viewContract).setCount(1)
    }
}