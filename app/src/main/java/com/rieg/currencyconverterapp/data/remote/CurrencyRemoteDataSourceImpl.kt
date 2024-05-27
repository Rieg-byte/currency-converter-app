package com.rieg.currencyconverterapp.data.remote

import com.rieg.currencyconverterapp.data.models.CurrencyDataResponse
import javax.inject.Inject

class CurrencyRemoteDataSourceImpl @Inject constructor(private val currencyApiService: CurrencyApiService): CurrencyRemoteDataSource {
    override suspend fun getCurrencyDataResponse(): CurrencyDataResponse = currencyApiService.getCurrencyDataResponse()
}