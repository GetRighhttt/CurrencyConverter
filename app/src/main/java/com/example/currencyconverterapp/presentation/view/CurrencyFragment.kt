package com.example.currencyconverterapp.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.databinding.FragmentCurrencyBinding
import com.example.currencyconverterapp.domain.util.CurrencyEvent
import com.example.currencyconverterapp.presentation.viewmodel.CurrencyViewModel
import kotlinx.coroutines.launch
import androidx.core.net.toUri


class CurrencyFragment : Fragment() {

    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CurrencyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        initializeViews()
    }

    private fun initializeViews() {
        binding.btnConvert.setOnClickListener {
            viewModel.convert(
                binding.etEnterNumber.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString()
            )
        }

        binding.github.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, getString(R.string.github_url).toUri())
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.conversion.collect { currency ->
                    when (currency) {
                        is CurrencyEvent.Success -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.tvResult.text = currency.resultText
                        }

                        is CurrencyEvent.Failure -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.tvResult.text = currency.errorText
                        }

                        is CurrencyEvent.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        CurrencyEvent.Empty -> binding.progressBar.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val CURRENCY_FRAGMENT = "CURRENCY_FRAGMENT"
    }
}
