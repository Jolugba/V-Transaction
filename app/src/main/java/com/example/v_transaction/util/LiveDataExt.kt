package com.example.v_transaction.util

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun <T : Any?> LifecycleOwner.observe(data: LiveData<T>, body: (T) -> Unit) {
    data.observe(this, Observer(body))
}
fun ViewModel.runIO(function: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
           function()
        } catch (e: Exception) {
            Log.e("Disapatcher","Unexpected error: ${e.message}")
        }
    }
}
