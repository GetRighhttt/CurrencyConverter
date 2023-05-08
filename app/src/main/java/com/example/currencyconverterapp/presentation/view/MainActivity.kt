package com.example.currencyconverterapp.presentation.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverterapp.databinding.ActivityMainBinding
import com.example.currencyconverterapp.presentation.viewmodel.CurrencyViewModel
import com.example.currencyconverterapp.presentation.viewmodel.CurrencyViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("Deprecation")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CurrencyViewModel

    @Inject
    lateinit var currencyViewModelFactory: CurrencyViewModelFactory

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(
                ConnectivityManager
                .EXTRA_NO_CONNECTIVITY, false)
            if (notConnected) {
                disconnected()
            } else {
                connected()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        Hide action bar and theme.
         */
        supportActionBar?.hide()
        this@MainActivity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        viewModel = ViewModelProvider(
            this, currencyViewModelFactory
        )[CurrencyViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    private fun disconnected() {
        binding.apply {
            fragmentContainerView.visibility = View.INVISIBLE
        }
        Toast.makeText(
            this@MainActivity, "Disconnected. Please reconnect...",
            Toast.LENGTH_LONG).show()
    }

    private fun connected() {
        binding.apply {
            fragmentContainerView.visibility = View.VISIBLE
        }
        Toast.makeText(
            this@MainActivity, "Connected...",
            Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}