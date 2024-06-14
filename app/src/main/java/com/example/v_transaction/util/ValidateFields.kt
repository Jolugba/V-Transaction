package com.example.v_transaction.util

import com.example.v_transaction.auth.login.model.ViewState

object ValidationUtils {
    fun validateFields(
        email: String,
        password: String,
        stateSetter: (ViewState) -> Unit
    ): Boolean {
        return when {
            email.isEmpty() -> {
                stateSetter(ViewState.ERROR("Email cannot be blank", "email"))
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                stateSetter(ViewState.ERROR("Provide a valid email address", "email"))
                false
            }
            password.isEmpty() -> {
                stateSetter(ViewState.ERROR("Password cannot be blank", "password"))
                false
            }
            else -> true
        }
    }
}
