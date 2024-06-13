package com.example.v_transaction.util

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

fun Fragment.launchFragment(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun Fragment.popFragment() {
    findNavController().popBackStack()
}

fun Fragment.navigateUp() {
    findNavController().navigateUp()
}

fun Fragment.showLongSnackBar(message: String) {
    val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
    val snackbarView: View = snackbar.view
    val snackTextView: TextView =
        snackbarView.findViewById(com.google.android.material.R.id.snackbar_text)
    snackTextView.maxLines = 3
    snackbar.show()
}

fun Fragment.showShortSnackBar(message: String) {
    val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
    val snackbarView: View = snackbar.view
    val snackTextView: TextView =
        snackbarView.findViewById(com.google.android.material.R.id.snackbar_text)
    snackTextView.maxLines = 3
    snackbar.show()
}




