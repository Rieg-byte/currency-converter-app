package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.remote.model.Valute
import com.example.currencyconverterapp.util.ResultStatus

interface CurrencyRepository {
    suspend fun getCurrencyList(): ResultStatus<List<Valute>>
    suspend fun getValute(charCode: String): ResultStatus<Valute>
}