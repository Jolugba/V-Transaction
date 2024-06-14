package com.example.v_transaction.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountHolder(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val profileImageResId: Int,
    val accountNumber: String,
    var balance: Double
)
