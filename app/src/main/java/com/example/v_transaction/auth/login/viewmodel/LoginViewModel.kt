package com.example.v_transaction.auth.login.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.v_transaction.auth.login.model.User
import com.example.v_transaction.auth.login.model.ViewState
import com.example.v_transaction.util.ValidationUtils
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    val state = MutableLiveData<ViewState>()
    fun signIn(email: String, password: String) {
        if (!ValidationUtils.validateFields(email, password) { state.value = it }) {
            return
        }

        state.value = ViewState.LOADING(true)
        viewModelScope.launch {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    state.value = ViewState.LOADING(false)
                    if (task.isSuccessful) {
                        val firebaseUser = task.result?.user
                        if (firebaseUser != null) {
                            val user = User(
                                uid = firebaseUser.uid,
                                email = email
                            )
                            state.value = ViewState.USER(user)
                        }
                    } else {
                        Log.e("error", "${task.exception?.message}")
                        state.value =
                            ViewState.ERROR("${task.exception?.message}", "signin")
                    }
                }
        }
    }

}




