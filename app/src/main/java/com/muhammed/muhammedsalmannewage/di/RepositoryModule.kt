package com.muhammed.muhammedsalmannewage.di

import com.muhammed.muhammedsalmannewage.data.repository.MetricRepositoryImpl
import com.muhammed.muhammedsalmannewage.domain.repository.MetricRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMetricRepository(
        impl: MetricRepositoryImpl
    ) : MetricRepository

}