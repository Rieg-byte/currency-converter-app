package com.rieg.currencyconverterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rieg.currencyconverterapp.domain.models.ThemeMode
import com.rieg.currencyconverterapp.ui.CurrencyConverterApp
import com.rieg.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainActivityViewModel: MainActivityViewModel = hiltViewModel()
            val mainActivityUiState by mainActivityViewModel.mainActivityUiState.collectAsState()
            CurrencyConverterAppTheme(darkTheme = shouldUseDarkTheme(mainActivityUiState)) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyConverterApp()
                }
            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(
    mainActivityUiState: MainActivityUiState,
): Boolean = when (mainActivityUiState.themeMode) {
    ThemeMode.LIGHT.name -> false
    ThemeMode.DARK.name -> true
    else -> isSystemInDarkTheme()
}
