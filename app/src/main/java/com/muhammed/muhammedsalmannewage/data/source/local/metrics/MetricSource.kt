package com.muhammed.muhammedsalmannewage.data.source.local.metrics

interface MetricSource {

    suspend fun getWeightsList(): List<Int>
    suspend fun getHeightsList(): List<Int>
    suspend fun getGenderList(): List<String>
}