package com.rieg.currencyconverterapp.data.mappers

import com.rieg.currencyconverterapp.data.models.ThemeModeParam
import com.rieg.currencyconverterapp.domain.models.ThemeMode

fun ThemeMode.toThemeModeParam(): ThemeModeParam {
    return when(this) {
        ThemeMode.SYSTEM -> ThemeModeParam.SYSTEM
        ThemeMode.DARK -> ThemeModeParam.DARK
        ThemeMode.LIGHT -> ThemeModeParam.LIGHT
    }
}