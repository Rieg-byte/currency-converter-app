package com.example.currencyconverterapp.data.remote

import com.example.currencyconverterapp.data.model.CurrencyData

interface CurrencyRemoteDataSource {
    suspend fun getCurrencyData(): CurrencyData
}