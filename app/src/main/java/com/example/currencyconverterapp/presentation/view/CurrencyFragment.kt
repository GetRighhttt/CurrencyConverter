package com.example.currencyconverterapp.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverterapp.R.*
import com.example.currencyconverterapp.R.color.*
import com.example.currencyconverterapp.databinding.FragmentCurrencyBinding
import com.example.currencyconverterapp.domain.util.CurrencyEvent
import com.example.currencyconverterapp.domain.util.Extensions.materialDialog
import com.example.currencyconverterapp.presentation.viewmodel.CurrencyViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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
        initializeViews()
        return binding!!.root
    }

    @SuppressLint("SetTextI18n")
    private fun initializeViews() {
        binding?.apply {
            btnConvert.setOnClickListener {
                viewModel.convert(
                    etEnterNumber.text.toString(),
                    spFromCurrency.selectedItem.toString(),
                    spToCurrency.selectedItem.toString()
                )
            }

            lifecycleScope.launchWhenStarted {
                viewModel.conversion.collect { currency ->
                    when (currency) {
                        is CurrencyEvent.Success -> {
                            progressBar.visibility = View.INVISIBLE
                            tvResult.text = currency.resultText
                        }

                        is CurrencyEvent.Failure -> {
                            progressBar.visibility = View.INVISIBLE
                            tvResult.text = "Error when retrieving currencies.."
                            materialDialog(
                                requireContext(), "ERROR", "It seems as though" +
                                        " your exchange could not be completed. Consider trying again."
                            )
                        }

                        is CurrencyEvent.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }

                        else -> {
                            Log.d(CURRENCY_FRAGMENT, "Currently at an empty state...")
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { it ->
                Log.d(CURRENCY_FRAGMENT, "Loading currencies")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val CURRENCY_FRAGMENT = "CURRENCY_FRAGMENT"
    }
}