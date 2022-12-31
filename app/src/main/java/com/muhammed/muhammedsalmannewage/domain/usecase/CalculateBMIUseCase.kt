package com.muhammed.muhammedsalmannewage.domain.usecase

import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMI
import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMIRequest
import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMIResult
import com.muhammed.muhammedsalmannewage.domain.model.bmi.WeightClass
import javax.inject.Inject
import kotlin.math.pow

class CalculateBMIUseCase @Inject constructor() {

    operator fun invoke(bmiRequest: BMIRequest): State<BMIResult> {
        val bmi = calculateBMI(bmiRequest)
        val weightClass = calculateWeightClass(bmi)

        return State.Success(
            data = BMIResult(
                weightClass = weightClass,
                bmi = BMI.fromFloat(bmi)
            )
        )
    }

    // Calculation -> bmi = weight / (heightInMeters)^2
    private fun calculateBMI(bmiRequest: BMIRequest): Float {
        return with(bmiRequest) {
            val heightInMeters = height / 100f
            val bmi = weight / heightInMeters.pow(2)
            // Taking the first 5 digits of the number
            val bmiTrimmed = bmi.toString().take(5).toFloat()
            bmiTrimmed
        }
    }

    private fun calculateWeightClass(bmi: Float): WeightClass {
        return when (bmi) {
            in 0f..18.4f -> {
                WeightClass.UNDER
            }
            in 18.5f..24.9f -> {
                WeightClass.HEALTHY
            }
            in 25f..29.9f -> {
                WeightClass.OVER
            }
            else -> {
                WeightClass.OBESE
            }
        }
    }
}
