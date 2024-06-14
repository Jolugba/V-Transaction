package com.example.v_transaction.auth.login.model

sealed class ViewState {
    data class USER(val user: User?) : ViewState()
    data class LOADING(val loading: Boolean = false) : ViewState()
    data class ERROR(val errorMessage: String, val type: String):ViewState()
}