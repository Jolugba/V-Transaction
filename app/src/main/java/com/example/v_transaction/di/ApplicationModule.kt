package com.example.v_transaction.di

import android.content.Context
import com.example.v_transaction.database.AppRoomDatabase
import com.example.v_transaction.database.AppRoomDatabase.Companion.getInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
    @Provides
    @Singleton
    fun providesRoomDatabase(
        @ApplicationContext applicationContext: Context
    ): AppRoomDatabase {
        return getInstance(applicationContext)!!
    }
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

}
