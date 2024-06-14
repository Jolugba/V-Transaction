package com.example.v_transaction.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.v_transaction.database.entity.AccountHolder

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts ORDER BY id ASC")
    fun getAllAccounts(): LiveData<List<AccountHolder>>

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    suspend fun getAccountById(accountId: Int): AccountHolder?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(account: AccountHolder)

    @Update
    suspend fun update(account: AccountHolder)
}
