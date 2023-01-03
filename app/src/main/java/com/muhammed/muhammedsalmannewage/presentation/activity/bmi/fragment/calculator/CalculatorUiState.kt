package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment.calculator

import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMIResult
import com.muhammed.muhammedsalmannewage.domain.model.bmi.Gender
/*
    * showAd -> when on, an Ad should appear.
    * selectedWeight -> current selected weight from the list on screen
    * selectedHeight -> current selected height from the list on screen
    * selectedGender -> current selected gender from the list on screen
    * bmiResult -> the result after calculation, should leave fragment when not null.
    * weights, heightsList and genderList -> lists to be propagated as ListViews on screen
 */
data class CalculatorUiState(
    val showAd: Boolean = false,
    val selectedWeight: Int = DEFAULT_WEIGHT,
    val selectedHeight: Int = DEFAULT_HEIGHT,
    val selectedGender: Gender = Gender.MALE,
    val bmiResult: BMIResult? = null,
    val weightsList: List<Int> = emptyList(),
    val heightsList: List<Int> = emptyList(),
    val genderList: List<Gender> = emptyList()
)

private const val DEFAULT_WEIGHT = 43
private const val DEFAULT_HEIGHT = 127

