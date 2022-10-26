package com.example.currencyconverterapp.data.api

import com.example.currencyconverterapp.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/latest")
    suspend fun getRates(
        @Query("base") base: String
    ) : Response<CurrencyResponse>
}