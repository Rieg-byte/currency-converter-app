package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.CurrencyData
import com.example.currencyconverterapp.data.remote.CurrencyRemoteDataSource
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val currencyRemoteDataSource: CurrencyRemoteDataSource) : CurrencyRepository {
    override suspend fun getCurrencyData(): CurrencyData {
        return currencyRemoteDataSource.getCurrencyData()
    }

    override suspend fun getCurrency(charCode: String): Currency? {
        val currencyData = currencyRemoteDataSource.getCurrencyData()
        return currencyData.currencies[charCode]
    }
}