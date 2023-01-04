package com.muhammed.muhammedsalmannewage.domain.repository

import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.metric.MetricsLists

interface MetricRepository {

    suspend fun getMetricsLists() : State<MetricsLists>
}