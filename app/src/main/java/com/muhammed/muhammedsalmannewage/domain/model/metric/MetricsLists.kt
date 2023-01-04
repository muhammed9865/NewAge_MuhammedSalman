package com.muhammed.muhammedsalmannewage.domain.model.metric

import com.muhammed.muhammedsalmannewage.domain.model.bmi.Gender

data class MetricsLists(
    val weightList: List<Int>,
    val heightList: List<Int>,
    val genderList: List<Gender>
)
