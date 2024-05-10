package com.rieg.currencyconverterapp.data.remote

import com.rieg.currencyconverterapp.data.model.CurrencyData
import javax.inject.Inject

class CurrencyRemoteDataSourceImpl @Inject constructor(private val currencyApiService: CurrencyApiService): CurrencyRemoteDataSource {
    override suspend fun getCurrencyData(): CurrencyData = currencyApiService.getCurrencyData()
}