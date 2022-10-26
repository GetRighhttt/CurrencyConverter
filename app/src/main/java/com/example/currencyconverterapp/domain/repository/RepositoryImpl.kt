package com.example.currencyconverterapp.domain.repository

import android.os.Build
import com.example.currencyconverterapp.BuildConfig
import com.example.currencyconverterapp.data.api.ApiService
import com.example.currencyconverterapp.data.model.CurrencyResponse
import com.example.currencyconverterapp.domain.util.Resource
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
): Repository {

    /*
    Method to get all the rates and check the state of the request
     */
    override suspend fun getRates(base: String, apikey: String): Resource<CurrencyResponse> {
        return try {
             val response = apiService.getRates(base, BuildConfig.API_KEY)
            val result = response.body()
            if((response.isSuccessful) && (result != null)) {
                Resource.Success(result)
            }
            else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unable to retrieve rates.")
        }
    }

}