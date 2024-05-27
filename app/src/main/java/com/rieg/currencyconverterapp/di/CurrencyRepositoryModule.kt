package com.rieg.currencyconverterapp.di

import com.rieg.currencyconverterapp.data.remote.CurrencyRemoteDataSource
import com.rieg.currencyconverterapp.data.remote.CurrencyRemoteDataSourceImpl
import com.rieg.currencyconverterapp.data.repository.CurrencyRepositoryImpl
import com.rieg.currencyconverterapp.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrencyRepositoryModule {
    @Binds
    abstract fun provideCurrencyRemoteDataSource(
        currencyRemoteDataSource: CurrencyRemoteDataSourceImpl
    ): CurrencyRemoteDataSource

    @Binds
    abstract fun provideCurrencyRepository(
        currencyRepository: CurrencyRepositoryImpl
    ): CurrencyRepository
}