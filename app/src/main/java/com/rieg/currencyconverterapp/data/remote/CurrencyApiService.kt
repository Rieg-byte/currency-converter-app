package com.rieg.currencyconverterapp.data.remote

import com.rieg.currencyconverterapp.data.models.CurrencyDataResponse
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("daily_json.js")
    suspend fun getCurrencyDataResponse(): CurrencyDataResponse
}