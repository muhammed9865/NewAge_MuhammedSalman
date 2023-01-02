package com.muhammed.muhammedsalmannewage.domain.usecase

import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.bmi.MetricsLists
import com.muhammed.muhammedsalmannewage.domain.repository.MetricRepository
import javax.inject.Inject

/*
    * The reason for this UseCase is that if ever the app needs to scale,
    * And fetch lists from different datasource,
    * No logic need to be done in the presentation layer
 */
class GetMetricsListsUseCase @Inject constructor(
    private val repository: MetricRepository
) {
    suspend operator fun invoke() : State<MetricsLists> = repository.getMetricsLists()

}