package com.example.currencyconverterapp.data.remote

import com.example.currencyconverterapp.data.model.CurrencyList

interface CurrencyRemoteDataSource {
    suspend fun getCurrencyList(): CurrencyList
}