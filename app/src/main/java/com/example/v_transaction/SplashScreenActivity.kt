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
import com.example.v_transaction.databinding.SplashActivityBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: SplashActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install the splash screen
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Perform animation

        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 1000 // 1 second
        fadeIn.fillAfter = true
        binding.splashLogo.startAnimation(fadeIn)

        // Delay for 3 seconds before navigating to the next activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3 seconds delay
    }

    }
