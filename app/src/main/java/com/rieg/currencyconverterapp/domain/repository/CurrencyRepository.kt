package com.rieg.currencyconverterapp.domain.repository

import com.rieg.currencyconverterapp.domain.models.Currency
import com.rieg.currencyconverterapp.domain.models.CurrencyData

interface CurrencyRepository {
    suspend fun getCurrencyData(): CurrencyData
    suspend fun getCurrency(charCode: String): Currency?
}