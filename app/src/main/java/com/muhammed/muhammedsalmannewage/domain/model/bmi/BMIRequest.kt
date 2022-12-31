package com.muhammed.muhammedsalmannewage.domain.model.bmi

// Weight in Kilograms
// Height in Centimeters
data class BMIRequest(
    val weight: Int,
    val height: Int,
    val gender: Gender
)

enum class Gender {
    MALE,
    FEMALE,
    UNDEFINED
}
