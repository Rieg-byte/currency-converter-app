package com.rieg.currencyconverterapp.ui.screens.home

import com.rieg.currencyconverterapp.data.model.Currency

sealed interface HomeUiState{
    data class Success(
        val timestamp: String,
        val listOfCurrency: List<Currency> = emptyList()
    ): HomeUiState
    object Loading: HomeUiState
    object Error: HomeUiState
}
