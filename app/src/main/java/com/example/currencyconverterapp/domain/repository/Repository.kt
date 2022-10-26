package com.example.currencyconverterapp.domain.repository

import com.example.currencyconverterapp.data.model.CurrencyResponse
import com.example.currencyconverterapp.domain.util.Resource

/*
Serves as layer between data sources and view model.
 */
interface Repository {
    suspend fun getRates(base: String, apikey: String) : Resource<CurrencyResponse>
}