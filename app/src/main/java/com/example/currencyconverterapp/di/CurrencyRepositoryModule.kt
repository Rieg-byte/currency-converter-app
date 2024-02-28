package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.data.remote.CurrencyRemoteDataSource
import com.example.currencyconverterapp.data.remote.CurrencyRemoteDataSourceImpl
import com.example.currencyconverterapp.data.repository.CurrencyRepository
import com.example.currencyconverterapp.data.repository.CurrencyRepositoryImpl
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