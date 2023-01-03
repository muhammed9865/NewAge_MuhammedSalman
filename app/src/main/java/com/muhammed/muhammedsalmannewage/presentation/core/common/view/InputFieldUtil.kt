package com.muhammed.muhammedsalmannewage.presentation.core.common.view

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.setImeAction(action: Int) {
    imeOptions = action
}

fun EditText.onDone(callback: (view: View) -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callback.invoke(this)
            return@setOnEditorActionListener true
        }
        false
    }
}