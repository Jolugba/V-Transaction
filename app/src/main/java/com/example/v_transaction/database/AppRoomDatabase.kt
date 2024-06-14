package com.example.v_transaction.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.v_transaction.database.daos.AccountDao
import com.example.v_transaction.database.daos.TransactionDao
import com.example.v_transaction.database.entity.AccountHolder
import com.example.v_transaction.database.entity.Transaction


@Database(
    entities = [
        AccountHolder::class, Transaction::class
    ],
    version = 1, exportSchema = false
)


abstract class AppRoomDatabase : RoomDatabase() {
    companion object {
        private val DATABASE_NAME = "V-Transaction"
        private var sInstance: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase? {
            if (sInstance == null) {
                synchronized(AppRoomDatabase::class.java) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppRoomDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return sInstance
        }
    }

    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao

}
