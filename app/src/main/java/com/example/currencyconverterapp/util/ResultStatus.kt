package com.example.currencyconverterapp.util

sealed class ResultStatus<T> (val data: T?, val message: String?) {
    class Success<T>(data: T): ResultStatus<T>(data, null)
    class Error<T>(message: String): ResultStatus<T>(null, message)
}