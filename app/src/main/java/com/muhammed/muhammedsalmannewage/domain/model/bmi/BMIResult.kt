package com.muhammed.muhammedsalmannewage.domain.model.bmi

data class BMIResult(
    val weightClass: WeightClass,
    val bmi: BMI
)

enum class WeightClass(
    val start: Float,
    val end: Float,
) {
    UNDER(
        0f,
        18.4f
    ),
    HEALTHY(
        18.5f,
        24.9f
    ),
    OVER(
        25f,
        29.9f
    ),
    OBESE(
        start = 30f,
        end = 100f
    ),

}

fun WeightClass.inRange(bmi: Float) : Boolean{
    return bmi in start .. end
}

fun WeightClass.name(): String {
    return buildString {
        append(name.first())
        append(name.takeLast(name.length - 1).lowercase())
    }
}

