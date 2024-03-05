package com.example.currencyconverterapp.ui.screens.currencyList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(private val currencyRepository: CurrencyRepository): ViewModel() {
    private val _currencyListUiState = MutableStateFlow<CurrencyListUiState>(CurrencyListUiState.Loading)
    val currencyListUiState = _currencyListUiState.asStateFlow()
    init {
        getCurrencyList()
    }

    fun repeat() {
        getCurrencyList()
    }

    fun updateCurrencyList() = viewModelScope.launch {
        try {
            val result = currencyRepository.getCurrencyData()
            val timestamp = result.timestamp
            val listOfCurrency = result.currencies.values.toList()
            _currencyListUiState.value = CurrencyListUiState.Success(
                timestamp = toNormalFormat(timestamp),
                listOfCurrency = listOfCurrency
            )
        } catch (e: IOException) {
            _currencyListUiState.value = CurrencyListUiState.Error
        } catch (e: HttpException) {
            _currencyListUiState.value = CurrencyListUiState.Error
        }
    }

    private fun getCurrencyList() = viewModelScope.launch {
        _currencyListUiState.value = CurrencyListUiState.Loading
        updateCurrencyList()
    }
}

fun toNormalFormat(timestamp: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
    val dateTime = OffsetDateTime.parse(timestamp, inputFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")
    return outputFormatter.format(dateTime)
}