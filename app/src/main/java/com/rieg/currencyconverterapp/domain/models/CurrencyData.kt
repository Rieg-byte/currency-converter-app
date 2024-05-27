package com.rieg.currencyconverterapp.domain.models

data class CurrencyData(
    val date: String,
    val previousDate: String,
    val previousURL: String,
    val timestamp: String,
    val currencies: List<Currency>
)
