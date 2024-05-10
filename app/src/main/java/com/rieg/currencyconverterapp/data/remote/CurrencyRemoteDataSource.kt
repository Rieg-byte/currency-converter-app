package com.rieg.currencyconverterapp.data.remote

import com.rieg.currencyconverterapp.data.model.CurrencyData

interface CurrencyRemoteDataSource {
    suspend fun getCurrencyData(): CurrencyData
}