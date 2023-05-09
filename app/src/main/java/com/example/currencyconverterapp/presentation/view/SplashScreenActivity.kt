package com.example.currencyconverterapp.presentation.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.currencyconverterapp.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
@Suppress("Deprecation")
class SplashScreenActivity : AppCompatActivity() {

    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        Remove action bar and theme from application:
         */
        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this@SplashScreenActivity, MainActivity::class.java)
            )
            finish()
        }, 1300)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}