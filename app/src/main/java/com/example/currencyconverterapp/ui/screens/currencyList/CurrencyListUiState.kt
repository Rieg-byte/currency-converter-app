package com.example.currencyconverterapp.ui.screens.currencyList

import com.example.currencyconverterapp.data.model.Currency

data class CurrencyListUiState(
    val listOfCurrency: List<Currency> = emptyList(),
)
