package com.example.currencyconverterapp.data.api

import com.example.currencyconverterapp.BuildConfig
import com.example.currencyconverterapp.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("/latest")
    suspend fun getRates(
        @Query("base") base: String,
        @Header("api-key") apiKey: String = BuildConfig.API_KEY
    ) : Response<CurrencyResponse>
}