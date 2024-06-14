package com.example.v_transaction.di

import android.content.Context
import android.content.SharedPreferences
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
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @SharedPrefsInfo
    fun provideSharedPrefsName(
        @ApplicationContext applicationContext: Context
    ): String {
        return applicationContext.packageName + "_shared_prefs"
    }

    @Provides
    @Singleton
    fun providesSharedPrefs(
        @SharedPrefsInfo prefsName: String, @ApplicationContext applicationContext: Context
    ): SharedPreferences {
        return applicationContext.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    }

}
