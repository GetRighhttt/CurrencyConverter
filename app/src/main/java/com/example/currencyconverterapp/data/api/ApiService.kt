package com.example.currencyconverterapp.data.api

import com.example.currencyconverterapp.BuildConfig
import com.example.currencyconverterapp.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("apikey: ${BuildConfig.API_KEY}")
    @GET("latest")
    suspend fun getRates(
        @Query("base") base: String
    ) : Response<CurrencyResponse>
}