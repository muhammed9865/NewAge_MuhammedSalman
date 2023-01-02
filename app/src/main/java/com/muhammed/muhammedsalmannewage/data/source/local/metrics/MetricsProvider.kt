package com.muhammed.muhammedsalmannewage.data.source.local.metrics

interface MetricsProvider {

    suspend fun getWeightsList(): List<Int>
    suspend fun getHeightsList(): List<Int>
    suspend fun getGenderList(): List<String>
}