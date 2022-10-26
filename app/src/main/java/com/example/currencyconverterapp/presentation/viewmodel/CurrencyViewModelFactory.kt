package com.example.currencyconverterapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverterapp.domain.repository.Repository
import com.example.currencyconverterapp.domain.util.DispatcherProvider

class CurrencyViewModelFactory(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(repository, dispatchers) as T
    }
}