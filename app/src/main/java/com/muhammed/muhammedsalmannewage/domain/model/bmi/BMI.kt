package com.muhammed.muhammedsalmannewage.domain.model.bmi

/*
    Used to access the parts of the BMI Result easily
 */
class BMI private constructor (
    // Integer part of the BMI result
    val integer: Int,
    // Fracture part of the BMI
    val fracture: Int
) {

    fun asString() = "$integer.$fracture"

    fun trimmed(): String {
        return buildString {
            append(integer)
            append('.')
            append(fracture.toString().take(2))
        }
    }

    companion object {
        fun fromFloat(bmi: Float) : BMI {
            val floatAsString = bmi.toString()
            val integer = floatAsString.substringBefore('.').toInt()
            val fracture = floatAsString.substringAfter('.').toInt()

            return BMI(integer, fracture)
        }
    }
}