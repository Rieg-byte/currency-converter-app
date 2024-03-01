package com.example.currencyconverterapp.ui.screens.currencyConverter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val currencyRepository: CurrencyRepository
): ViewModel() {
    private val charCode: String = checkNotNull(savedStateHandle["charCode"])
    private val _currencyConverterUiState =
        MutableStateFlow<CurrencyConverterUiState>(CurrencyConverterUiState(fromCurrency = charCode))
    val currencyConverterUiState = _currencyConverterUiState.asStateFlow()
    private var amountCurrency = 0.0
    private var nominal = 0

    init {
        getCurrency(charCode)
    }

    fun repeat() {
        getCurrency(charCode)
    }

    fun updateValue(value: String) {
        val amount = value.toDoubleOrNull()
        _currencyConverterUiState.update {
            if (amount != null) {
                it.copy(
                    fromValue = value,
                    toValue = convert(amount).toString(),
                    wrongValueError = false
                )
            } else if(value.isBlank()) {
                it.copy(
                    fromValue = "0",
                    toValue = "0.0"
                )
            }else {
                it.copy(
                    fromValue = value,
                    wrongValueError = true
                )
            }
        }
    }

    private fun convert(value: Double): Double {
        return (value * amountCurrency) / nominal
    }

    private fun convert(value: Int): Double {
        return (value * amountCurrency) / nominal
    }

    private fun getCurrency(charCode: String) = viewModelScope.launch {
        try {
            loading()
            val result = currencyRepository.getCurrency(charCode)
            if (result != null) {
                amountCurrency = result.value
                nominal = result.nominal
                _currencyConverterUiState.update {
                    it.copy(
                        fromValue = nominal.toString(),
                        toValue = convert(nominal).toString(),
                        error = false,
                        isLoading = false
                    )
                }
            } else {
                error()
            }
        } catch (e: HttpException) {
            error()
        } catch (e: IOException) {
            error()
        }
    }

    private fun error() {
        _currencyConverterUiState.update {
            it.copy(
                error = true,
                isLoading = false
            )
        }
    }

    private fun loading() {
        _currencyConverterUiState.update {
            it.copy(
                error = false,
                isLoading = true
            )
        }
    }
}