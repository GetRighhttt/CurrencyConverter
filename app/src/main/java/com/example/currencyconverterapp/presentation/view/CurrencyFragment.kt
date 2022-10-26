package com.example.currencyconverterapp.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.R.*
import com.example.currencyconverterapp.R.color.*
import com.example.currencyconverterapp.databinding.FragmentCurrencyBinding
import com.example.currencyconverterapp.domain.util.CurrencyEvent
import com.example.currencyconverterapp.presentation.viewmodel.CurrencyViewModel


class CurrencyFragment : Fragment() {

    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: CurrencyViewModel

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)

        viewModel = (activity as MainActivity).viewModel

        binding!!.apply {
            btnConvert.setOnClickListener {
                viewModel.convert(
                    etEnterNumber.text.toString(),
                    spFromCurrency.selectedItem.toString(),
                    spToCurrency.selectedItem.toString()
                )
            }

            lifecycleScope.launchWhenStarted {
                viewModel.conversion.collect { event ->
                    when (event) {
                        is CurrencyEvent.Success -> {
                            progressBar.visibility = View.INVISIBLE
                            tvResult.text = event.resultText.toString()
                        }
                        is CurrencyEvent.Failure -> {
                            progressBar.visibility = View.INVISIBLE
                            tvResult.text = event.errorText.toString()
                        }
                        is CurrencyEvent.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        else -> Unit
                    }
                }
            }
        }

        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}