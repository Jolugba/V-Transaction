package com.example.v_transaction.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.v_transaction.R

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
var sharedOptions: RequestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
fun ImageView.loadCircleImageFromUri(imageUrl: Int) {
    Glide.with(this)
        .load(imageUrl)
        .apply(sharedOptions)
        .centerCrop()
        .error(R.drawable.ic_error)
        .into(this)
}




