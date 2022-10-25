package com.example.currencyconverterapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.currencyconverterapp.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@Suppress("Deprecation")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        Remove action bar and theme from application:
         */
        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                startActivity(
                    Intent(this@SplashScreenActivity, MainActivity::class.java)
                )
                finish()
            }
        }, 3500)

    }
}