package com.muhammed.muhammedsalmannewage.data.source.local.metrics

import javax.inject.Inject


// This class is faking a network call to a server to get metrics.
class MetricsProviderImpl @Inject constructor(): MetricsProvider {

    override suspend fun getWeightsList(): List<Int> = getWeights()

    override suspend fun getHeightsList(): List<Int> = getHeights()

    override suspend fun getGenderList(): List<String> = getGenders()

    private fun getWeights() = List(size = WEIGHTS_COUNT) { weight ->
        weight + 1
    }

    private fun getHeights() = List(size = HEIGHTS_COUNT) { weight ->
        weight + 1
    }

    private fun getGenders() = listOf(
        "Male", "Female"
    )

    companion object {
        private const val WEIGHTS_COUNT = 250
        private const val HEIGHTS_COUNT = 250
    }
}