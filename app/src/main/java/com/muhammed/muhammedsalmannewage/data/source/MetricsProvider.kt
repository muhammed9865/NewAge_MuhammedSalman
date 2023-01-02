package com.muhammed.muhammedsalmannewage.data.source

interface MetricsProvider {

    suspend fun getWeightsList(): List<Int>
    suspend fun getHeightsList(): List<Int>
    suspend fun getGenderList(): List<String>
}