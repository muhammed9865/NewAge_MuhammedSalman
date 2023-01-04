package com.muhammed.muhammedsalmannewage.di

import com.muhammed.muhammedsalmannewage.data.source.local.metrics.MetricSource
import com.muhammed.muhammedsalmannewage.data.source.local.metrics.MetricSourceImpl
import com.muhammed.muhammedsalmannewage.data.source.local.sceenshot.ScreenshotSource
import com.muhammed.muhammedsalmannewage.data.source.local.sceenshot.ScreenshotSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindMetricsProvider(impl: MetricSourceImpl) : MetricSource

    @Binds
    abstract fun bindScreenshotSource(impl: ScreenshotSourceImpl) : ScreenshotSource

}