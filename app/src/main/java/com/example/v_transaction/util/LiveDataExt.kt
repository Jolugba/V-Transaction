package com.example.v_transaction.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any?> LifecycleOwner.observe(data: LiveData<T>, body: (T) -> Unit) {
    data.observe(this, Observer(body))
}

