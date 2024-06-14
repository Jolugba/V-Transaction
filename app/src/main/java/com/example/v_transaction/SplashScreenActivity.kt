package com.example.v_transaction

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.v_transaction.auth.AuthenticationActivity
import com.example.v_transaction.dashboard.MainActivity
import com.example.v_transaction.databinding.SplashActivityBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: SplashActivityBinding
    @Inject
    lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 1000
        fadeIn.fillAfter = true
        binding.splashLogo.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            checkUser()
        }, 3000)
    }
    private fun checkUser() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }
        finish()
    }

    }
