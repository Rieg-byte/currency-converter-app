package com.rieg.currencyconverterapp.data.remote

import com.rieg.currencyconverterapp.data.models.CurrencyDataResponse

interface CurrencyRemoteDataSource {
    suspend fun getCurrencyDataResponse(): CurrencyDataResponse
}