package com.example.currencyconverterapp.ui.screens.home

import com.example.currencyconverterapp.data.model.Currency

data class HomeUiState(
    val amount: String = "",
    val inputCurrency: String = "USD",
    val outputCurrency: String = "RUB",
    val convertedAmount: String = "0.0",
    val listOfCurrency: List<Currency> = emptyList(),
    val currentRate: Double = 0.0
)
