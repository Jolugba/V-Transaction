package com.example.v_transaction.auth.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    val state = MutableLiveData<ForgetPasswordViewState>()

    fun sendPasswordResetEmail(email: String) {
        if (!validateEmail(email)) {
            return
        }

        state.value = ForgetPasswordViewState.LOADING(true)
        viewModelScope.launch {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                state.value = ForgetPasswordViewState.LOADING(false)
                if (task.isSuccessful) {
                    state.value = ForgetPasswordViewState.SUCCESS("Password reset email sent successfully.")
                } else {
                    val errorMessage = task.exception?.message ?: "Unknown error occurred."
                    state.value = ForgetPasswordViewState.ERROR(errorMessage,"Other")
                }
            }
    }
    }


    fun validateEmailField(email: String) {
        if (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            state.value = ForgetPasswordViewState.CLEAR_ERROR
        } else {
            validateEmail(email)
        }
    }

    private fun validateEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                state.value = ForgetPasswordViewState.ERROR("Email cannot be blank.","email")
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                state.value = ForgetPasswordViewState.ERROR("Provide a valid email address.","email")
                false
            }
            else -> true
        }
    }
}
sealed class ForgetPasswordViewState {
   data class LOADING(val isLoading:Boolean) : ForgetPasswordViewState()
    data class SUCCESS(val message: String) : ForgetPasswordViewState()
    data class ERROR(val errorMessage: String,val type:String) : ForgetPasswordViewState()
    object CLEAR_ERROR : ForgetPasswordViewState()

}

