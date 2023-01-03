package com.muhammed.muhammedsalmannewage.presentation.core.common.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


fun View.hideKeyboard() {
    val ime: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    ime.hideSoftInputFromWindow(windowToken, 0)
}