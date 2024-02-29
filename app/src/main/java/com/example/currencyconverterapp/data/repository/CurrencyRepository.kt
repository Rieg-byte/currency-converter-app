package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.CurrencyData

interface CurrencyRepository {
    suspend fun getCurrencyData(): CurrencyData
    suspend fun getCurrency(charCode: String): Currency?
}