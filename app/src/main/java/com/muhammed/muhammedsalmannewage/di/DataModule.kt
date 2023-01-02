package com.muhammed.muhammedsalmannewage.di

import com.muhammed.muhammedsalmannewage.data.source.MetricsProvider
import com.muhammed.muhammedsalmannewage.data.source.MetricsProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindMetricsProvider(impl: MetricsProviderImpl) : MetricsProvider

}