package com.example.currencyconverterapp.ui.screens.currencyConverter

data class CurrencyConverterUiState (
    val fromCurrency: String = "",
    val fromValue: String = "",
    val toCurrency: String = "RUB",
    val toValue: String = "",
    val error: Boolean = false,
    val isLoading: Boolean = true,
    val wrongValueError: Boolean = false
)
