package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.remote.CurrencyRemoteDataSourceImpl
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val currencyRemoteDataSource: CurrencyRemoteDataSourceImpl) : CurrencyRepository {
    override suspend fun getCurrencyList(): List<Currency> {
        return currencyRemoteDataSource.getCurrencyData().currency.values.toList()
    }
    override suspend fun getValute(charCode: String): Currency? {
        val result = currencyRemoteDataSource.getCurrencyData()
        return result.currency[charCode]
    }
}