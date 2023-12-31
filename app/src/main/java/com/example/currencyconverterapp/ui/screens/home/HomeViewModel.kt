package com.example.currencyconverterapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.repository.CurrencyRepositoryImpl
import com.example.currencyconverterapp.util.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val currencyRepository: CurrencyRepositoryImpl): ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()
    init {
        updateInputCurrency("USD")
        getCurrencyList()
    }

    fun updateAmount(amount: String = _homeUiState.value.amount) {
        if (amount.toDoubleOrNull() != null) {
            _homeUiState.update { value ->
                value.copy(
                    amount = amount
                )
            }
        } else {
            _homeUiState.update { value ->
                value.copy(
                    amount = ""
                )
            }
        }
        converteAmount()
    }

    private fun converteAmount() {
        val amount = _homeUiState.value.amount.toDoubleOrNull()
        if (amount != null) {
            val currentRate = _homeUiState.value.currentRate
            val result = amount * currentRate
            _homeUiState.update {
                it.copy(
                    convertedAmount = result.toString()
                )
            }
        } else {
            _homeUiState.update {
                it.copy(
                    convertedAmount = "0.0"
                )
            }
        }
    }

    private fun getCurrencyList() = viewModelScope.launch {
        when(val result = currencyRepository.getCurrencyList()){
            is ResultStatus.Success -> {
                _homeUiState.update { value ->
                    value.copy(
                        listOfCurrency = result.data ?: emptyList()
                    )
                }
            }
            is ResultStatus.Error -> {
                _homeUiState.update { value ->
                    value.copy(
                        listOfCurrency = emptyList()
                    )
                }
            }
        }

    }
    fun updateInputCurrency(charCode: String) = viewModelScope.launch {
        when(val result = currencyRepository.getValute(charCode)) {
            is ResultStatus.Success -> {
                _homeUiState.update {
                        it.copy(
                            inputCurrency = charCode,
                            currentRate = result.data?.value ?: 0.0
                        )
                    }
                updateAmount()
            }
            is ResultStatus.Error -> {}
        }
    }
}