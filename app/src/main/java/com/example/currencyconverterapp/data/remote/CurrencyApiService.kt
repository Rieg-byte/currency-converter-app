package com.example.currencyconverterapp.data.remote

import com.example.currencyconverterapp.data.model.CurrencyData
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("daily_json.js")
    suspend fun getCurrencyData(): CurrencyData
}