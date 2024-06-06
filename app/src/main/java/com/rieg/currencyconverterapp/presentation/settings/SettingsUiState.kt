package com.rieg.currencyconverterapp.presentation.settings


sealed interface SettingsUiState {
    data object Loading: SettingsUiState
    data class Success(
        val themeMode: String
    ): SettingsUiState
}