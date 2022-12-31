package com.muhammed.muhammedsalmannewage.presentation.activity.bmi

import androidx.lifecycle.ViewModel
import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    // Created to access it from the Result fragment
    // As a way of shared communication
    // Set by CalculatorFragment after calculation done
    var bmiResult: BMIResult? = null


}