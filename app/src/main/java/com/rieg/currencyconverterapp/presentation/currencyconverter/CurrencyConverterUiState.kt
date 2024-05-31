package com.rieg.currencyconverterapp.presentation.currencyconverter

sealed interface CurrencyConverterUiState {
    data class Success(
        val fromCurrency: String = "",
        val fromValue: String = "",
        val toCurrency: String = "RUB",
        val toValue: String = "",
    ) : CurrencyConverterUiState
    object Loading : CurrencyConverterUiState
    object Error : CurrencyConverterUiState
}
