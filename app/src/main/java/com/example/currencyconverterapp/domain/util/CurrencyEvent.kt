package com.example.currencyconverterapp.domain.util

/*
   Currency event class used for stateflow.
    */
sealed class CurrencyEvent {
    data class Success(val resultText: String) : CurrencyEvent()
    data class Failure(val errorText: String) : CurrencyEvent()
    object Loading : CurrencyEvent()
    object Empty : CurrencyEvent()
}