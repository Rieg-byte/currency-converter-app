package com.example.currencyconverterapp.ui.screens.currencyList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(currencyListViewModel: CurrencyListViewModel = viewModel()) {
    val homeUiState by currencyListViewModel.homeUiState.collectAsState()
}



@Composable
fun CurrencyCard(){

}