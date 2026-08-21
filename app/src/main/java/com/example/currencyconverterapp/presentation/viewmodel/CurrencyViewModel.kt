package com.example.currencyconverterapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.domain.model.Rates
import com.example.currencyconverterapp.domain.repository.Repository
import com.example.currencyconverterapp.domain.util.CurrencyEvent
import com.example.currencyconverterapp.domain.util.DispatcherProvider
import com.example.currencyconverterapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round
import kotlin.time.Duration.Companion.milliseconds

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

    private var conversionJob: Job? = null

    fun convert(
        amountOfCurrency: String,
        fromCountryCurrency: String,
        toCountryCurrency: String
    ) {
        conversionJob?.cancel()

        val fromAmount = amountOfCurrency.trim().toDoubleOrNull()
        if (fromAmount == null || !fromAmount.isFinite() || fromAmount < 0) {
            _conversion.update { CurrencyEvent.Failure("Inaccurate amount entered.") }
            return
        }

        val fromCurrency = fromCountryCurrency.uppercase()
        val toCurrency = toCountryCurrency.uppercase()

        if (fromCurrency == toCurrency) {
            _isLoading.update { false }
            _conversion.update {
                CurrencyEvent.Success("$fromAmount $fromCurrency = $fromAmount $toCurrency")
            }
            return
        }

        conversionJob = viewModelScope.launch(dispatchers.mainCD) {
            try {
                delay(500.milliseconds)
                _conversion.update { CurrencyEvent.Loading }
                _isLoading.update { true }

                when (val ratesResponse = repository.getRates(fromCurrency)) {
                    is Resource.Error -> _conversion.update {
                        CurrencyEvent.Failure(
                            ratesResponse.message ?: "Unable to retrieve rates."
                        )
                    }

                    is Resource.Success -> {
                        val rates = ratesResponse.data?.rates
                        val rate = rates?.let {
                            getRateForCurrency(toCurrency, it)?.toDoubleOrNull()
                        }

                        if (rate == null || !rate.isFinite() || rate < 0) {
                            _conversion.update {
                                CurrencyEvent.Failure("We have an unexpected error...")
                            }
                        } else {
                            val convertedCurrency = round(fromAmount * rate * 100) / 100
                            _conversion.update {
                                CurrencyEvent.Success(
                                    "$fromAmount $fromCurrency = " +
                                            "$convertedCurrency $toCurrency"
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _conversion.update {
                            CurrencyEvent.Loading
                        }
                        _isLoading.update { true }
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _conversion.update {
                    CurrencyEvent.Failure(e.message ?: "Unable to retrieve rates.")
                }
            } finally {
                _isLoading.update { false }
            }
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
                "TRY" -> rates.tRY
                else -> null
            }
    }
}
