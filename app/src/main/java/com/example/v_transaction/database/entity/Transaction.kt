package com.example.v_transaction.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sourceAccountId: Int,
    val destinationAccountId: Int,
    val amount: Double,
    val timestamp: Long
)

