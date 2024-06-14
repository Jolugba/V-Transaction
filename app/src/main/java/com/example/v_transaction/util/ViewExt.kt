package com.example.v_transaction.util

import android.view.View

/**
 * Convenience function to hide View
 *
 */
fun View.hide(onlyInvisible: Boolean = false) {
    this.visibility = if (onlyInvisible) View.INVISIBLE else View.GONE
}

/**
 * Convenience function to show View
 *
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.toggleVisibility(show: Boolean) {
    if (show) this.show() else this.hide()
}

/**
 * Convenience function check if view is visible
 *
 */
fun View.isVisible() = this.visibility == View.VISIBLE




