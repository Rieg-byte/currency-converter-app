package com.rieg.currencyconverterapp.presentation.currencyconverter

sealed interface CurrencyConverterUiState {
    data class Success(
        val inputCurrencyLabel: String = "",
        val inputCurrency: String = "",
        val inputValue: String = "",
        val outputCurrencyLabel: String = "",
        val outputCurrency: String = "",
        val outputValue: String = ""
    ) : CurrencyConverterUiState
    object Loading : CurrencyConverterUiState
    object Error : CurrencyConverterUiState
}
