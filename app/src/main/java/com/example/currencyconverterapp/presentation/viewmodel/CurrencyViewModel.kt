package com.example.currencyconverterapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.domain.model.Rates
import com.example.currencyconverterapp.domain.repository.Repository
import com.example.currencyconverterapp.domain.util.CurrencyEvent
import com.example.currencyconverterapp.domain.util.DispatcherProvider
import com.example.currencyconverterapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

/*
Best way to inject into view model. Pass dispatchers for testing.
 */
@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    /*
    Pass empty event initially because we need a value there for state flow.
     */
    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun convert(
        amountOfCurrency: String,
        fromCountryCurrency: String,
        toCountryCurrency: String
    ) {
        val fromAmount = amountOfCurrency.toFloatOrNull()
        if (fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Inaccurate amount entered.")
            return
        }

        viewModelScope.launch(dispatchers.mainCD) {
            delay(500)
            _conversion.value = CurrencyEvent.Loading
            _isLoading.value = true

            when (val ratesResponse = repository.getRates(fromCountryCurrency)) {
                is Resource.Error -> _conversion.value =
                    CurrencyEvent.Failure(ratesResponse.message!!)

                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(toCountryCurrency, rates)
                    if (rate == null) {
                        _conversion.value =
                            CurrencyEvent.Failure("We have an unexpected error...")
                    } else {
                        val convertedCurrency = round(fromAmount * rate.toFloat() * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCountryCurrency = " +
                                    "$convertedCurrency $toCountryCurrency"
                        )
                    }
                }

                is Resource.Loading -> {
                    _conversion.value = CurrencyEvent.Loading
                    _isLoading.value = true
                }
            }
            _isLoading.value = false
        }
    }

    companion object {
        private fun getRateForCurrency(currency: String, rates: Rates) =
            when (currency) {
                "CAD" -> rates.cAD
                "HKD" -> rates.hKD
                "ISK" -> rates.iSK
                "EUR" -> rates.eUR
                "PHP" -> rates.pHP
                "DKK" -> rates.dKK
                "HUF" -> rates.hUF
                "CZK" -> rates.cZK
                "AUD" -> rates.aUD
                "RON" -> rates.rON
                "SEK" -> rates.sEK
                "IDR" -> rates.iDR
                "INR" -> rates.iNR
                "BRL" -> rates.bRL
                "RUB" -> rates.rUB
                "HRK" -> rates.hRK
                "JPY" -> rates.jPY
                "THB" -> rates.tHB
                "CHF" -> rates.cHF
                "SGD" -> rates.sGD
                "PLN" -> rates.pLN
                "BGN" -> rates.bGN
                "CNY" -> rates.cNY
                "NOK" -> rates.nOK
                "NZD" -> rates.nZD
                "ZAR" -> rates.zAR
                "USD" -> rates.uSD
                "MXN" -> rates.mXN
                "ILS" -> rates.iLS
                "GBP" -> rates.gBP
                "KRW" -> rates.kRW
                "MYR" -> rates.mYR
                else -> null
            }
    }
}