package com.rieg.currencyconverterapp.data.repository

import com.rieg.currencyconverterapp.data.datastore.CurrencyConverterPreferences
import com.rieg.currencyconverterapp.data.mappers.toThemeModeParam
import com.rieg.currencyconverterapp.domain.models.ThemeMode
import com.rieg.currencyconverterapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val currencyConverterPreferences: CurrencyConverterPreferences
): SettingsRepository {
    override val themeMode: Flow<String>
        get() = currencyConverterPreferences.themeMode
    override suspend fun setThemeMode(themeMode: ThemeMode) = currencyConverterPreferences.setThemeMode(themeMode.toThemeModeParam())
}