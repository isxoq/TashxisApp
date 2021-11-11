package com.example.tashxis.business.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.tashxis.R


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}


fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

private var dialog: AlertDialog? = null

val Fragment.progressDialog: AlertDialog?
    get() {
        if (dialog == null)
            context?.let {
                dialog = AlertDialog.Builder(it, R.style.WrapContentDialog)
                    .setView(R.layout.progress_dialog)
                    .create()
            }
        return dialog
    }