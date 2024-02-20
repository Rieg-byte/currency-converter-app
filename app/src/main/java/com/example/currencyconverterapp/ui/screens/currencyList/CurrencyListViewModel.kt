package com.example.currencyconverterapp.ui.screens.currencyList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.repository.CurrencyRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(private val currencyRepository: CurrencyRepositoryImpl): ViewModel() {
    private val _currencyListUiState = MutableStateFlow<CurrencyListUiState>(CurrencyListUiState.Loading)
    val currencyListUiState = _currencyListUiState.asStateFlow()
    init {
        getCurrencyList()
    }

    fun repeat() {
        getCurrencyList()
    }

    fun refresh() = viewModelScope.launch {
        try {
            val result = currencyRepository.getCurrencyList()
            _currencyListUiState.value = CurrencyListUiState.Success(
                listOfCurrency = result
            )
        } catch (e: IOException) {
            _currencyListUiState.value = CurrencyListUiState.Error
        } catch (e: HttpException) {
            _currencyListUiState.value = CurrencyListUiState.Error
        }

    }

    private fun getCurrencyList() = viewModelScope.launch {
        try {
            _currencyListUiState.value = CurrencyListUiState.Loading
            val result = currencyRepository.getCurrencyList()
            _currencyListUiState.value = CurrencyListUiState.Success(
                listOfCurrency = result
            )
        } catch (e: IOException) {
            _currencyListUiState.value = CurrencyListUiState.Error
        } catch (e: HttpException) {
            _currencyListUiState.value = CurrencyListUiState.Error
        }

    }

}