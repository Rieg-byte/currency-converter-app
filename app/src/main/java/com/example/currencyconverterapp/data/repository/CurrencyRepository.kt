package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.model.Currency

interface CurrencyRepository {
    suspend fun getTimestamp(): String
    suspend fun getCurrencyList(): List<Currency>
}