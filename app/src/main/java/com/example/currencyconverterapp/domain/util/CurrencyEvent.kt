package com.example.currencyconverterapp.domain.util

/*
    Currency event class used for stateflow.
     */
sealed class CurrencyEvent {
    class Success(resultText: String) : CurrencyEvent()
    class Failure(errorText: String) : CurrencyEvent()
    object Loading : CurrencyEvent()
    object Empty : CurrencyEvent()
}