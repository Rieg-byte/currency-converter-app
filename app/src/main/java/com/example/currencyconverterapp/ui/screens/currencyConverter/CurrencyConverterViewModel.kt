package com.example.currencyconverterapp.ui.screens.currencyConverter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val currencyRepository: CurrencyRepository
): ViewModel() {
    private val _currencyConverterUiState =
        MutableStateFlow<CurrencyConverterUiState>(CurrencyConverterUiState.Loading)
    val currencyConverterUiState = _currencyConverterUiState.asStateFlow()
    private val charCode: String = checkNotNull(savedStateHandle["charCode"])
    private var amountCurrency = 0.0
    private var nominal = 0.0
    init {
        getCurrency(charCode)
    }

    fun repeat() {
        getCurrency(charCode)
    }

    private fun convert(value: Double) {
        val convertedValue = (value * amountCurrency) / nominal
        _currencyConverterUiState.value = CurrencyConverterUiState.Success(
            toValue = convertedValue.toString()
        )
    }
    private fun getCurrency(charCode: String) = viewModelScope.launch {
        try {
            _currencyConverterUiState.value = CurrencyConverterUiState.Loading
            val currency = currencyRepository.getCurrency(charCode)
            if (currency != null) {
                amountCurrency = currency.value
                nominal = currency.nominal.toDouble()
                _currencyConverterUiState.value = CurrencyConverterUiState.Success(
                    fromCurrency = currency.charCode
                )
                convert(nominal)
            } else {
                _currencyConverterUiState.value = CurrencyConverterUiState.Error
            }
        } catch (e: HttpException) {
            _currencyConverterUiState.value = CurrencyConverterUiState.Error
        } catch (e: IOException) {
            _currencyConverterUiState.value = CurrencyConverterUiState.Error
        }
    }
}