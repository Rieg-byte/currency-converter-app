package com.rieg.currencyconverterapp.data.mappers

import com.rieg.currencyconverterapp.data.models.CurrencyDataResponse
import com.rieg.currencyconverterapp.data.models.CurrencyResponse
import com.rieg.currencyconverterapp.domain.models.Currency
import com.rieg.currencyconverterapp.domain.models.CurrencyData

fun CurrencyDataResponse.toCurrencyData(): CurrencyData {
    val currencyData = CurrencyData(
        date = date,
        previousDate = previousDate,
        previousURL = previousURL,
        timestamp = timestamp,
        currencies = currencies.map { it.value.toCurrency() }
    )
    return currencyData
}


fun CurrencyResponse.toCurrency(): Currency {
    val currency = Currency(
        id = id,
        numCode = numCode,
        charCode = charCode,
        nominal = nominal,
        name = name,
        value = value,
        previous = previous
    )
    return currency
}