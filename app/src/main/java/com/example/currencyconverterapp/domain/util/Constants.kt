package com.example.currencyconverterapp.domain.util

import com.example.currencyconverterapp.BuildConfig

object Constants {
    val API_KEY: String
        get() = BuildConfig.API_KEY

    val BASE_URL: String
        get() = BuildConfig.BASE_URL
}
