package com.example.v_transaction.dashboard.viewmodel

import DummyData
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.v_transaction.database.AppRoomDatabase
import com.example.v_transaction.database.entity.AccountHolder
import com.example.v_transaction.database.entity.Transaction
import com.example.v_transaction.util.runIO
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(val firebaseAuth: FirebaseAuth,val database: AppRoomDatabase) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> = _state
    private val accountDao = database.accountDao()
    private val transactionDao =database.transactionDao()

    val accounts: LiveData<List<AccountHolder>> = accountDao.getAllAccounts()

    val transactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()
    private val _viewState = MutableLiveData<ViewState>()
init {
    getUserAccountHolders()
}
    fun getUserAccountHolders(){
        runIO {
            if (accountDao.getAllAccounts().value.isNullOrEmpty()) {
                DummyData.getDummyAccountHolders().forEach {
                    accountDao.insert(it)
                }
            }
        }
    }

    fun transferMoney(sourceId: Int, destinationId: Int, amount: Double) {
        runIO {
            if (amount <= 0 || amount < 10) {
                _state.postValue(ViewState.Error("Invalid amount. Amount must be greater than or equal to 10."))
                return@runIO
            }

            // Set loading state to true initially
            _state.postValue(ViewState.Loading(true))

            try {
                val sourceAccount = accountDao.getAccountById(sourceId)
                val destinationAccount = accountDao.getAccountById(destinationId)

                Log.d("HomeViewModel", "Source Account: $sourceAccount, Destination Account: $destinationAccount")

                if (sourceAccount == null || destinationAccount == null) {
                    _state.postValue(ViewState.Error("Invalid account selected"))
                    return@runIO
                }

                if (sourceAccount.balance >= amount) {
                    try {
                        sourceAccount.balance -= amount
                        destinationAccount.balance += amount

                        accountDao.update(sourceAccount)
                        accountDao.update(destinationAccount)

                        val transaction = Transaction(
                            id = 0,
                            sourceAccountId = sourceId,
                            destinationAccountId = destinationId,
                            amount = amount,
                            timestamp = System.currentTimeMillis()
                        )
                        transactionDao.insert(transaction)

                        // **Success message with fixed typo**
                        _state.postValue(ViewState.Success("Transaction successful"))
                        Log.e("HomeViewModel", "Transaction successful")

                    } catch (e: Exception) {
                        _state.postValue(ViewState.Error("Transaction failed: ${e.message}"))
                        Log.e("HomeViewModel", "Transaction failed: ${e.message}", e) // Log the exception for debugging
                    }
                } else {
                    _state.postValue(ViewState.Error("Insufficient balance in source account"))
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Transaction failed: ${e.message}", e) // Log the exception for debugging
                _state.postValue(ViewState.Error("Transaction failed: ${e.message}"))
            } finally {
                Log.e("HomeViewModel", "Setting Loading to false")
                _state.postValue(ViewState.Loading(false))
            }
        }
    }






    fun getUserDetails(): String {
        val user = firebaseAuth.currentUser
        user?.let {
            for (profile in it.providerData) {
                val email = profile.email
                if (email != null) {
                    return extractInitialsFromEmail(email)
                }
            }
        }
        return " "
    }

    private fun extractInitialsFromEmail(email: String?): String {
        return try {
            if (email == null) return " "
            val namePart = email.substringBefore("@")
            val nameParts = namePart.split(".")
            when {
                nameParts.size > 1 -> {
                    "${nameParts[0].first().uppercaseChar()}.${nameParts[1].first().uppercaseChar()}"
                }
                nameParts.isNotEmpty() -> {
                    val initials = nameParts[0].take(2).map { it.uppercaseChar() }
                    if (initials.size > 1) {
                        "${initials[0]}.${initials[1]}"
                    } else {
                        "${initials[0]}"
                    }
                }
                else -> {
                    " "
                }
            }
        } catch (e: Exception) {
            " "
        }
    }

}
sealed class ViewState {
    data class Loading(val isLoading: Boolean) : ViewState()
    data class Error(val errorMessage: String) : ViewState()
    data class Success(val message:String):ViewState()
}




