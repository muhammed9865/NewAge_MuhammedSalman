package com.muhammed.muhammedsalmannewage.domain

import com.muhammed.muhammedsalmannewage.domain.model.bmi.Gender

object DomainConstants {
    const val SCREENSHOT_EXT = ".png"
    const val SCREENSHOT_DELETE_DELAY = 20000L

    // Used for the BMI calculator as the default values of metrics
    const val DEFAULT_SELECTED_WEIGHT = 43
    const val DEFAULT_SELECTED_HEIGHT = 127
    val DEFAULT_SELECTED_GENDER = Gender.FEMALE

}