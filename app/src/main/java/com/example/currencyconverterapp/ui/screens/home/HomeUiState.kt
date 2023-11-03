package com.example.currencyconverterapp.ui.screens.home

import com.example.currencyconverterapp.data.remote.model.Valute

data class HomeUiState(
    val amount: String = "",
    val inputCurrency: String = "USD",
    val outputCurrency: String = "RUB",
    val convertedAmount: String = "0.0",
    val listOfCurrency: List<Valute> = emptyList(),
    val currentRate: Double = 0.0
)
