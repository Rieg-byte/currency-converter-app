package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.remote.CurrencyRemoteDataSource
import com.example.currencyconverterapp.data.remote.model.Valute
import com.example.currencyconverterapp.util.ResultStatus
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val currencyRemoteDataSource: CurrencyRemoteDataSource) : CurrencyRepository {
    override suspend fun getCurrencyList(): ResultStatus<List<Valute>> {
        return try {
            val result = currencyRemoteDataSource.getCurrencyData().valute.values.toList()
            ResultStatus.Success(result)
        } catch (e: Exception) {
            ResultStatus.Error(e.message ?: "Error")
        }
    }
    override suspend fun getValute(charCode: String): ResultStatus<Valute> {
        return try {
            val result = currencyRemoteDataSource.getCurrencyData()
            val valute = result.valute[charCode]
            if (valute != null) {
                ResultStatus.Success(valute)
            } else {
                ResultStatus.Error("Error")
            }
        } catch (e: Exception) {
            ResultStatus.Error(e.message ?: "Error")
        }
    }
}