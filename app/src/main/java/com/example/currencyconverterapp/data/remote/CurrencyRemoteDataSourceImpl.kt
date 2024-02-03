package com.example.currencyconverterapp.data.remote

import com.example.currencyconverterapp.data.model.CurrencyList
import javax.inject.Inject

class CurrencyRemoteDataSourceImpl @Inject constructor(private val currencyApiService: CurrencyApiService): CurrencyRemoteDataSource {
    override suspend fun getCurrencyList(): CurrencyList = currencyApiService.getCurrencyList()
}