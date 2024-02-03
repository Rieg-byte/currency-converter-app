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
    private val _homeUiState = MutableStateFlow(CurrencyListUiState())
    val homeUiState = _homeUiState.asStateFlow()
    init {
        getCurrencyList()
    }

    private fun getCurrencyList() = viewModelScope.launch {
        try {
            val result = currencyRepository.getCurrencyList()
            _homeUiState.update {
                it.copy(
                    listOfCurrency = result
                )
            }
        } catch (e: IOException) {
            _homeUiState.update {
                it.copy(
                    listOfCurrency = emptyList()
                )
            }
        } catch (e: HttpException) {
            _homeUiState.update {
                it.copy(
                    listOfCurrency = emptyList()
                )
            }
        }

    }

}