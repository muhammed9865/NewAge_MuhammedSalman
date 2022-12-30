package com.muhammed.muhammedsalmannewage.domain.model.bmi

data class BMIResult(
    val weightClass: WeightClass,
    val bmi: Float
)

enum class WeightClass {
    UNDER,
    HEALTHY,
    OVER,
    OBESE,
}