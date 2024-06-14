package com.example.v_transaction.util

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

fun Fragment.launchFragment(direction: NavDirections) {
    try {
        findNavController().navigate(direction)
    } catch (e: IllegalArgumentException) {
        Log.e("NavigationError", "Navigation action/destination cannot be found: ${e.message}")
    }
}
fun Fragment.launchFragment(destination: Int) {
    try {
        findNavController().navigate(destination)
    } catch (e: IllegalArgumentException) {
        Log.e("NavigationError", "Navigation action/destination cannot be found: ${e.message}")
    }
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
fun Fragment.showErrorDialog(errorMessage: String) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle("Error")
        .setMessage(errorMessage)
        .setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}




