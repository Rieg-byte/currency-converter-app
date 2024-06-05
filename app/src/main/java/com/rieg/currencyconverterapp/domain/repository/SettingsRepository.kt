package com.rieg.currencyconverterapp.domain.repository

import com.rieg.currencyconverterapp.domain.models.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val themeMode: Flow<String>
    suspend fun setThemeMode(themeMode: ThemeMode)
}