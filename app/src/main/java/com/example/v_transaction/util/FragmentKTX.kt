package com.example.v_transaction.util

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
        e.printStackTrace()
    }
}
fun Fragment.launchFragment(destination: Int) {
    try {
        findNavController().navigate(destination)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}
fun Fragment.showLongSnackBar(message: String) {
    val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
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





