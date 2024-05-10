package com.rieg.currencyconverterapp.data.remote

import com.rieg.currencyconverterapp.data.model.CurrencyData
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("daily_json.js")
    suspend fun getCurrencyData(): CurrencyData
}