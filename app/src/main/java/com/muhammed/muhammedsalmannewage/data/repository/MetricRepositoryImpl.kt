package com.muhammed.muhammedsalmannewage.data.repository

import com.muhammed.muhammedsalmannewage.data.mapper.gender.GenderMapper
import com.muhammed.muhammedsalmannewage.data.source.local.metrics.MetricSource
import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.metric.MetricsLists
import com.muhammed.muhammedsalmannewage.domain.repository.MetricRepository
import javax.inject.Inject

class MetricRepositoryImpl @Inject constructor(
    private val metricSource: MetricSource,
) : MetricRepository {

    override suspend fun getMetricsLists(): State<MetricsLists> {
        // Provide lists from data source
        val weights = metricSource.getWeightsList()
        val heights = metricSource.getHeightsList()
        val genders = metricSource.getGenderList()

        // Map genders to domain layer
        val gendersMapped = genders.map {
            val mapStatus = GenderMapper.mapGenderToDomain(it)
            if (mapStatus.isSuccessful()) {
                mapStatus.data!!
            } else {
                // Else block to print the throwable stack,
                // The data should be not null since it's default is Gender.UNDEFINED
                val throwable = mapStatus.throwable
                throwable?.printStackTrace()
                mapStatus.data!!
            }
        }

        return State.Success(
            MetricsLists(
                weightList = weights,
                heightList = heights,
                genderList = gendersMapped
            )
        )
    }
}