package com.example.currencyconverterapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    @SerialName("ID") val id: String,
    @SerialName("NumCode") val numCode: String,
    @SerialName("CharCode") val charCode: String,
    @SerialName("Nominal") val nominal: Int,
    @SerialName("Name") val name: String,
    @SerialName("Value") val value: Double,
    @SerialName("Previous") val previous: Double
)
