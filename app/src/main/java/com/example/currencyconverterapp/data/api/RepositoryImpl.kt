package com.example.currencyconverterapp.data.api

import com.example.currencyconverterapp.domain.model.CurrencyResponse
import com.example.currencyconverterapp.domain.repository.Repository
import com.example.currencyconverterapp.domain.util.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
): Repository {

    /*
    Method to get all the rates and check the state of the request
     */
    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
             val response = apiService.getRates(base)
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