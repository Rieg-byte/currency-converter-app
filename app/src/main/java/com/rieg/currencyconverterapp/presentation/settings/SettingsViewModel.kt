package com.rieg.currencyconverterapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rieg.currencyconverterapp.domain.models.ThemeMode
import com.rieg.currencyconverterapp.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepository): ViewModel() {
    val settingsUiState: StateFlow<SettingsUiState> = settingsRepository.themeMode.map { themeMode ->
        SettingsUiState.Success(themeMode = themeMode)
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5.seconds.inWholeMilliseconds),
        initialValue = SettingsUiState.Loading
    )

    fun updateThemeMode(themeMode: ThemeMode) = viewModelScope.launch {
        settingsRepository.setThemeMode(themeMode)
    }

}