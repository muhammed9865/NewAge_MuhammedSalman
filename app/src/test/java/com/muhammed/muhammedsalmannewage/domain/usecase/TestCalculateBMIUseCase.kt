package com.muhammed.muhammedsalmannewage.domain.usecase

import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMIRequest
import com.muhammed.muhammedsalmannewage.domain.model.bmi.Gender
import com.muhammed.muhammedsalmannewage.domain.model.bmi.WeightClass
import org.junit.Before
import org.junit.Test

class TestCalculateBMIUseCase {

    private lateinit var useCase: CalculateBMIUseCase

    @Before
    fun setUp() {
        useCase = CalculateBMIUseCase()
    }

    private val bmiRequest = BMIRequest(
        weight = 80,
        height = 175,
        gender = Gender.MALE
    )

    private val correctBmi = 26.12f
    private val correctWeightClass = WeightClass.OVER

    @Test
    fun `returned bmi is correct`() {
        // When
        val bmiResult = useCase.invoke(bmiRequest)
        val bmi = bmiResult.data!!.bmi.trimmed().toFloat()

        println(bmi)
        // Then
        assert(bmi == correctBmi)
    }

    @Test
    fun `returned weightClass is correct`() {
        // When
        val bmiResult = useCase.invoke(bmiRequest)
        val weightClass = bmiResult.data!!.weightClass

        println(weightClass)
        // Then
        assert(weightClass == correctWeightClass)
    }
}