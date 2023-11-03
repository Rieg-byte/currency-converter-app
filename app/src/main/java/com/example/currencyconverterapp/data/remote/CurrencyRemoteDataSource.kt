package com.example.currencyconverterapp.data.remote

import com.example.currencyconverterapp.data.remote.model.CurrencyData
import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor(private val currencyApiService: CurrencyApiService) {
    suspend fun getCurrencyData(): CurrencyData = currencyApiService.getCurrencyData()
}