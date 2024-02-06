package com.example.currencyconverterapp.ui.screens.currencyList

import com.example.currencyconverterapp.data.model.Currency

sealed interface CurrencyListUiState{
    data class Success(
        val listOfCurrency: List<Currency> = emptyList()
    ): CurrencyListUiState
    object Loading: CurrencyListUiState
    object Error: CurrencyListUiState
}
