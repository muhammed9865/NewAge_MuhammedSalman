package com.muhammed.muhammedsalmannewage.domain.usecase

import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.bmi.*
import javax.inject.Inject
import kotlin.math.pow

class CalculateBMIUseCase @Inject constructor() {

    operator fun invoke(bmiRequest: BMIRequest): State<BMIResult> {
        val bmi = calculateBMI(bmiRequest)
        val weightClass = calculateWeightClass(bmi)
        val pi = calculatePonderalIndex(bmiRequest = bmiRequest)

        return State.Success(
            data = BMIResult(
                weightClass = weightClass,
                bmi = BMI.fromFloat(bmi),
                pi = pi
            )
        )
    }

    private fun calculatePonderalIndex(bmiRequest: BMIRequest): PonderalIndex {
        return with(bmiRequest) {
            val heightInMeters = height / 100f
            val pi = weight / heightInMeters.pow(3)
            PonderalIndex(
                pi = pi
            )
        }
    }

        // Calculation -> bmi = weight / (heightInMeters)^2
        private fun calculateBMI(bmiRequest: BMIRequest): Float {
            return with(bmiRequest) {
                val heightInMeters = height / 100f
                val bmi = weight / heightInMeters.pow(2)
                bmi
            }
        }

        private fun calculateWeightClass(bmi: Float): WeightClass {
            var weightClass = WeightClass.UNDER
            for (type in WeightClass.values()) {
                if (type.inRange(bmi)) {
                    weightClass = type
                    break
                }
            }
            return weightClass
        }
    }
