package com.rieg.currencyconverterapp.ui.screens.currencyList

import com.rieg.currencyconverterapp.data.model.Currency

sealed interface CurrencyListUiState{
    data class Success(
        val timestamp: String,
        val listOfCurrency: List<Currency> = emptyList()
    ): CurrencyListUiState
    object Loading: CurrencyListUiState
    object Error: CurrencyListUiState
}
