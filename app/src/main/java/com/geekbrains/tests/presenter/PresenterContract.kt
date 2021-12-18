package com.geekbrains.tests.presenter

import android.view.View

internal interface PresenterContract {
    fun onAttach(view: View)
    fun onDetach()
}