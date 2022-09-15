package com.md.simpleconverter.converters

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class Utils {

    fun removeFocus(focusedView: EditText, context: Context) {
        if (focusedView.hasFocus()) {
            focusedView.clearFocus()

            context.hideKeyboard(focusedView)
        }
    }

    private fun Context.hideKeyboard(view: View) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}