package com.rieg.currencyconverterapp.data.repository

import com.rieg.currencyconverterapp.data.model.Currency
import com.rieg.currencyconverterapp.data.model.CurrencyData

interface CurrencyRepository {
    suspend fun getCurrencyData(): CurrencyData
    suspend fun getCurrency(charCode: String): Currency?
}