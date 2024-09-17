package com.example.currencyconverterapp.data.api

import com.example.currencyconverterapp.domain.model.CurrencyResponse
import com.example.currencyconverterapp.domain.util.Constants
import pub.devrel.easypermissions.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("apikey: ${Constants.API_KEY}")
    @GET("latest")
    suspend fun getRates(
        @Query("base") base: String
    ): Response<CurrencyResponse>
}