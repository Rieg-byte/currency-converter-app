package com.rieg.currencyconverterapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDataResponse(
    @SerialName("Date") val date: String,
    @SerialName("PreviousDate") val previousDate: String,
    @SerialName("PreviousURL") val previousURL: String,
    @SerialName("Timestamp") val timestamp: String,
    @SerialName("Valute") val currencies: Map<String, CurrencyResponse>
)
