package com.rieg.currencyconverterapp.presentation.currencyconverter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rieg.currencyconverterapp.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _currencyConverterUiState = MutableStateFlow<CurrencyConverterUiState>(CurrencyConverterUiState.Loading)
    val currencyConverterUiState = _currencyConverterUiState.asStateFlow()

    private var isConverterSwap = false
    private var conversionRate = 0.0
    private var nominal = 0
    private var charCode: String = checkNotNull(savedStateHandle["charCode"])

    init {
        getCurrency(charCode)
    }

    fun onRepeat() {
        getCurrency(charCode)
    }

    fun onUpdateCurrencyValue(value: String) {
        val inputValue = value.toDoubleOrNull() ?: 0.0
        val outputValue = convert(inputValue)
        val currentState = _currencyConverterUiState.value as? CurrencyConverterUiState.Success
        if (currentState != null) {
            _currencyConverterUiState.value = currentState.copy(
                inputValue = value,
                outputValue = outputValue.toNormalFormat()
            )
        }
    }

    private fun getCurrency(charCode: String) = viewModelScope.launch {
        _currencyConverterUiState.value = CurrencyConverterUiState.Loading
        try {
            val result = currencyRepository.getCurrency(charCode)
            if (result != null) {
                conversionRate = result.value
                nominal = result.nominal
                val inputValue = nominal.toString()
                val outputValue = convert(inputValue.toDouble()).toNormalFormat()
                _currencyConverterUiState.value = CurrencyConverterUiState.Success(
                    inputCurrencyLabel = result.name,
                    inputCurrency = charCode,
                    inputValue = inputValue,
                    outputCurrencyLabel = "Российский рубль",
                    outputCurrency = "RUB",
                    outputValue = outputValue
                )
            } else {
                _currencyConverterUiState.value = CurrencyConverterUiState.Error
            }
        } catch (e: HttpException) {
            _currencyConverterUiState.value = CurrencyConverterUiState.Error
        } catch (e: IOException) {
            _currencyConverterUiState.value = CurrencyConverterUiState.Error
        }
    }

    fun onSwapConverter() {
        isConverterSwap = !isConverterSwap
        val currentState = _currencyConverterUiState.value as? CurrencyConverterUiState.Success
        if (currentState != null) {
            val swappedInputCurrencyLabel = currentState.outputCurrencyLabel
            val swappedInputCurrency = currentState.outputCurrency
            val swappedInputValue = currentState.outputValue
            val swappedOutputCurrencyLabel = currentState.inputCurrencyLabel
            val swappedOutputCurrency = currentState.inputCurrency
            val swappedOutputValue = currentState.inputValue
            _currencyConverterUiState.value = currentState.copy(
                inputCurrencyLabel = swappedInputCurrencyLabel,
                inputCurrency = swappedInputCurrency,
                inputValue = swappedInputValue,
                outputCurrencyLabel = swappedOutputCurrencyLabel,
                outputCurrency = swappedOutputCurrency,
                outputValue = swappedOutputValue
            )
        }
    }

    private fun convert(value: Double): Double {
        return if (isConverterSwap) {
            value * (nominal / conversionRate)
        } else {
            (value * conversionRate) / nominal
        }
    }
}

private fun Double.toNormalFormat(): String = "%.2f".format(Locale.ROOT, this)
