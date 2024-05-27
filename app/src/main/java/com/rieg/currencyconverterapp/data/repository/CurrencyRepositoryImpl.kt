package com.rieg.currencyconverterapp.data.repository

import com.rieg.currencyconverterapp.data.mappers.toCurrency
import com.rieg.currencyconverterapp.data.mappers.toCurrencyData
import com.rieg.currencyconverterapp.data.remote.CurrencyRemoteDataSource
import com.rieg.currencyconverterapp.domain.models.Currency
import com.rieg.currencyconverterapp.domain.models.CurrencyData
import com.rieg.currencyconverterapp.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val currencyRemoteDataSource: CurrencyRemoteDataSource) :
    CurrencyRepository {
    override suspend fun getCurrencyData(): CurrencyData {
        return currencyRemoteDataSource.getCurrencyDataResponse().toCurrencyData()
    }

    override suspend fun getCurrency(charCode: String): Currency? {
        val currencyData = currencyRemoteDataSource.getCurrencyDataResponse()
        return currencyData.currencies[charCode]?.toCurrency()
    }
}