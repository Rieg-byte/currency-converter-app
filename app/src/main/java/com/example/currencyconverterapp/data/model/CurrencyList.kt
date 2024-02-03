package com.example.currencyconverterapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyList(
    @SerialName("Date") val date: String,
    @SerialName("PreviousDate") val previousDate: String,
    @SerialName("PreviousURL") val previousURL: String,
    @SerialName("Timestamp") val timestamp: String,
    @SerialName("Valute") val currency: Map<String, Currency>
)
