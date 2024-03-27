package com.example.currencyconverterapp.domain.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Rates(
    @SerializedName("AUD")
    val aUD: String,
    @SerializedName("BGN")
    val bGN: String,
    @SerializedName("EUR")
    val eUR: String,
    @SerializedName("BRL")
    val bRL: String,
    @SerializedName("CAD")
    val cAD: String,
    @SerializedName("CHF")
    val cHF: String,
    @SerializedName("CNY")
    val cNY: String,
    @SerializedName("CZK")
    val cZK: String,
    @SerializedName("DKK")
    val dKK: String,
    @SerializedName("GBP")
    val gBP: String,
    @SerializedName("HKD")
    val hKD: String,
    @SerializedName("HRK")
    val hRK: String,
    @SerializedName("HUF")
    val hUF: String,
    @SerializedName("IDR")
    val iDR: String,
    @SerializedName("ILS")
    val iLS: String,
    @SerializedName("INR")
    val iNR: String,
    @SerializedName("ISK")
    val iSK: String,
    @SerializedName("JPY")
    val jPY: String,
    @SerializedName("KRW")
    val kRW: String,
    @SerializedName("MXN")
    val mXN: String,
    @SerializedName("MYR")
    val mYR: String,
    @SerializedName("NOK")
    val nOK: String,
    @SerializedName("NZD")
    val nZD: String,
    @SerializedName("PHP")
    val pHP: String,
    @SerializedName("PLN")
    val pLN: String,
    @SerializedName("RON")
    val rON: String,
    @SerializedName("RUB")
    val rUB: String,
    @SerializedName("SEK")
    val sEK: String,
    @SerializedName("SGD")
    val sGD: String,
    @SerializedName("THB")
    val tHB: String,
    @SerializedName("TRY")
    val tRY: String,
    @SerializedName("USD")
    val uSD: String,
    @SerializedName("ZAR")
    val zAR: String
) : Parcelable