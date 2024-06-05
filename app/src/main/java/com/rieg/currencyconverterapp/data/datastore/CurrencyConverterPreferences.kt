package com.rieg.currencyconverterapp.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rieg.currencyconverterapp.data.models.ThemeModeParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyConverterPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        val THEME = stringPreferencesKey(name = "theme")
        const val TAG = "CurrencyConverterPreferences"
    }
    suspend fun setThemeMode(themeModeParam: ThemeModeParam) {
        dataStore.edit { preferences ->
            preferences[THEME] = themeModeParam.name
        }
    }

    val themeMode: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[THEME] ?: ThemeModeParam.SYSTEM.name
    }
}