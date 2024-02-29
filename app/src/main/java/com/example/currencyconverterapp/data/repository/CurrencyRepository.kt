package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.model.CurrencyData

interface CurrencyRepository {
    suspend fun getCurrencyData(): CurrencyData
}