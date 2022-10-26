package com.example.currencyconverterapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.databinding.ActivityMainBinding
import com.example.currencyconverterapp.presentation.viewmodel.CurrencyViewModel
import com.example.currencyconverterapp.presentation.viewmodel.CurrencyViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("Deprecation")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CurrencyViewModel

    @Inject
    lateinit var currencyViewModelFactory: CurrencyViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
}