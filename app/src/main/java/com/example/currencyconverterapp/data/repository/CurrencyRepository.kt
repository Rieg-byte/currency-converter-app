package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.model.Currency

interface CurrencyRepository {
    suspend fun getCurrencyList(): List<Currency>
    suspend fun getValute(charCode: String): Currency?
}