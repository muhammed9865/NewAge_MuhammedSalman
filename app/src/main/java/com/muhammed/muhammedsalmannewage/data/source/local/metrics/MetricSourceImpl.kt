package com.muhammed.muhammedsalmannewage.data.source.local.metrics

import com.muhammed.muhammedsalmannewage.domain.DomainConstants
import javax.inject.Inject


// This class is faking a network call to a server to get metrics.
class MetricSourceImpl @Inject constructor(): MetricSource {

    override suspend fun getWeightsList(): List<Int> = getWeights()

    override suspend fun getHeightsList(): List<Int> = getHeights()

    override suspend fun getGenderList(): List<String> = getGenders()

    private fun getWeights() = List(size = DomainConstants.WEIGHTS_COUNT) { weight ->
        weight + 1
    }

    private fun getHeights() = List(size = DomainConstants.HEIGHTS_COUNT) { weight ->
        weight + 1
    }

    private fun getGenders() = listOf(
        "Male", "Female"
    )

    companion object {

    }
}