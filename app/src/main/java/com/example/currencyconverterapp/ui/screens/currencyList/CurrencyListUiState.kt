package com.example.currencyconverterapp.ui.screens.currencyList

import com.example.currencyconverterapp.data.model.Currency

data class CurrencyListUiState(
    val amount: String = "",
    val inputCurrency: String = "USD",
    val outputCurrency: String = "RUB",
    val convertedAmount: String = "0.0",
    val listOfCurrency: List<Currency> = emptyList(),
    val currentRate: Double = 0.0
)
