package com.rieg.currencyconverterapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rieg.currencyconverterapp.domain.models.ThemeMode
import com.rieg.currencyconverterapp.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    val mainActivityUiState: StateFlow<MainActivityUiState> = settingsRepository.themeMode.map { themeMode ->
        MainActivityUiState(themeMode = themeMode)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
        initialValue = MainActivityUiState(themeMode = ThemeMode.SYSTEM.name)
    )
}

