package com.example.v_transaction.dashboard.viewmodel

import DummyData
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
            val isValidAmount = amount >= 10
            val areAccountsDifferent = sourceId != destinationId

            if (!isValidAmount) {
                _state.postValue(ViewState.Error("Invalid amount. Amount must be greater than or equal to 10."))
                return@runIO
            }

            if (!areAccountsDifferent) {
                _state.postValue(ViewState.Error("Source and destination accounts cannot be the same."))
                return@runIO
            }

            _state.postValue(ViewState.Loading(true))

            try {
                val sourceAccount = accountDao.getAccountById(sourceId)
                val destinationAccount = accountDao.getAccountById(destinationId)

                if (sourceAccount == null || destinationAccount == null) {
                    _state.postValue(ViewState.Error("Invalid account selected"))
                    return@runIO
                }

                if (sourceAccount.balance >= amount) {
                    performTransaction(
                        sourceAccount = sourceAccount,
                        destinationAccount = destinationAccount,
                        amount = amount
                    )
                } else {
                    _state.postValue(ViewState.Error("Insufficient balance in source account"))
                }
            } catch (e: Exception) {
                _state.postValue(ViewState.Error("Transaction failed: ${e.message}"))
            } finally {
                _state.postValue(ViewState.Loading(false))
            }
        }
    }

    private suspend fun performTransaction(
        sourceAccount: AccountHolder,
        destinationAccount: AccountHolder,
        amount: Double
    ) {
        try {
            sourceAccount.balance -= amount
            destinationAccount.balance += amount

            accountDao.update(sourceAccount)
            accountDao.update(destinationAccount)

            val transaction = Transaction(
                id = 0,
                sourceAccountId = sourceAccount.id,
                destinationAccountId = destinationAccount.id,
                destinationAccountName = destinationAccount.name,
                sourceAccountName = sourceAccount.name,
                amount = amount,
                timestamp = System.currentTimeMillis()
            )
            transactionDao.insert(transaction)
            _state.postValue(ViewState.Success("Transaction successful"))
        } catch (e: Exception) {
            _state.postValue(ViewState.Error("Transaction failed: ${e.message}"))
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




