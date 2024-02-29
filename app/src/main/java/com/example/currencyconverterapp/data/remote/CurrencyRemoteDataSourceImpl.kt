package com.example.currencyconverterapp.data.remote

import com.example.currencyconverterapp.data.model.CurrencyData
import javax.inject.Inject

class CurrencyRemoteDataSourceImpl @Inject constructor(private val currencyApiService: CurrencyApiService): CurrencyRemoteDataSource {
    override suspend fun getCurrencyData(): CurrencyData = currencyApiService.getCurrencyData()
}