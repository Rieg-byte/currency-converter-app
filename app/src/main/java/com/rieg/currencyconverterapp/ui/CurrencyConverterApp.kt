package com.rieg.currencyconverterapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.rieg.currencyconverterapp.presentation.navigation.CurrencyConverterNavHost

@Composable
fun CurrencyConverterApp() {
    val navController = rememberNavController()
    CurrencyConverterNavHost(navController = navController)
}