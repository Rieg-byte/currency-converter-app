package com.rieg.currencyconverterapp.di

import com.rieg.currencyconverterapp.data.repository.SettingsRepositoryImpl
import com.rieg.currencyconverterapp.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsRepositoryModule {
    @Binds
    abstract fun provideSettingRepository(settingsRepository: SettingsRepositoryImpl): SettingsRepository
}