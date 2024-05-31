package com.rieg.currencyconverterapp.presentation.home

import com.rieg.currencyconverterapp.domain.models.Currency

sealed interface HomeUiState{
    data class Success(
        val timestamp: String,
        val listOfCurrency: List<Currency> = emptyList()
    ): HomeUiState
    data object Loading: HomeUiState
    data object Error: HomeUiState
}
