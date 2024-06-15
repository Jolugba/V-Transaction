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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val database: AppRoomDatabase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> = _state
    private val accountDao = database.accountDao()
    private val transactionDao = database.transactionDao()
    val accounts: LiveData<List<AccountHolder>> = accountDao.getAllAccounts()
    val transactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()
    private var sourceAccountId: Int = 0
    private var destinationAccountId: Int = 0
    private var transferAmount: Double = 0.0

    init {
        getUserAccountHolders()
    }

    private fun getUserAccountHolders() {
        runIO {
            if (accountDao.getAllAccounts().value.isNullOrEmpty()) {
                DummyData.getDummyAccountHolders().forEach {
                    accountDao.insert(it)
                }
            }
        }
    }

    private fun postState(viewState: ViewState) {
        _state.postValue(viewState)
    }

    fun transferMoney(sourceId: Int, destinationId: Int, amount: Double) {
        sourceAccountId = sourceId
        destinationAccountId = destinationId
        transferAmount = amount
        runIO {
            val isValidAmount = amount >= 10
            val areAccountsDifferent = sourceId != destinationId

            when {
                !isValidAmount -> postState(ViewState.Error("Invalid amount. Amount must be greater than or equal to 10."))
                !areAccountsDifferent -> postState(ViewState.Error("Source and destination accounts cannot be the same."))
                else -> {
                    val sourceAccount = accountDao.getAccountById(sourceId)
                    val destinationAccount = accountDao.getAccountById(destinationId)

                    when {
                        sourceAccount == null || destinationAccount == null -> postState(
                            ViewState.Error(
                                "Invalid account selected"
                            )
                        )

                        sourceAccount.balance < amount -> postState(ViewState.Error("Insufficient balance in source account"))
                        else -> {
                            val sourceAccountName = sourceAccount.name
                            val destinationAccountName = destinationAccount.name
                            postState(
                                ViewState.Confirm(
                                    "Do you want to transfer \$$amount from account " + "$sourceAccountName to account $destinationAccountName?"
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun onTransferConfirmed() {
        runIO {
            postState(ViewState.Loading(true))
            try {
                val sourceAccount = accountDao.getAccountById(sourceAccountId)
                val destinationAccount = accountDao.getAccountById(destinationAccountId)

                if (sourceAccount == null || destinationAccount == null) {
                    postState(ViewState.Error("Invalid account selected"))
                    return@runIO
                }
                performTransaction(sourceAccount, destinationAccount, transferAmount)
            } catch (e: Exception) {
                postState(ViewState.Error("Transaction failed: ${e.message}"))
            } finally {
                postState(ViewState.Loading(false))
            }
        }
    }

    private suspend fun performTransaction(
        sourceAccount: AccountHolder, destinationAccount: AccountHolder, amount: Double
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
            withContext(Dispatchers.Main) {
                postState(ViewState.Success("Your Transaction was successful"))
                Log.e("here","$transaction\n Successful")
            }
        } catch (e: Exception) {
            postState(ViewState.Error("Transaction failed: ${e.message}"))
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
                    "${nameParts[0].first().uppercaseChar()}.${
                        nameParts[1].first().uppercaseChar()
                    }"
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
    data class Success(val message: String) : ViewState()
    data class Confirm(val message: String) : ViewState()
}




