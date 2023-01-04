package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMIRequest
import com.muhammed.muhammedsalmannewage.domain.model.bmi.Gender
import com.muhammed.muhammedsalmannewage.domain.usecase.CalculateBMIUseCase
import com.muhammed.muhammedsalmannewage.domain.usecase.GetMetricsListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculateBMIUseCase: CalculateBMIUseCase,
    private val metricsListsUseCase: GetMetricsListsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CalculatorUiState())
    val state = _state.asStateFlow()

    // To access the state value faster
    private val stateAccess get() = state.value

    init {
        initMetricsLists()
    }

    private fun initMetricsLists() {
        viewModelScope.launch(Dispatchers.IO) {
            val listsResult = metricsListsUseCase.invoke()
            if (listsResult.isSuccessful()) {
                val lists = listsResult.data!!
                updateState(
                    stateAccess.copy(
                        weightsList = lists.weightList,
                        heightsList = lists.heightList,
                        genderList = lists.genderList,
                    )
                )
            }
        }
    }

    fun setWeight(weight: Int) {
        updateState(
            stateAccess.copy(selectedWeight = weight)
        )
    }

    fun setHeight(height: Int) {
        updateState(
            stateAccess.copy(selectedHeight = height)
        )
    }

    fun setGender(gender: Gender) {
        updateState(
            stateAccess.copy(selectedGender = gender)
        )
    }

    fun calculate() {
        val request = getBMIRequest()
        val result = calculateBMIUseCase.invoke(request)
        if (result.isSuccessful()) {
            updateState(
                stateAccess.copy(
                    showAd = true,
                    bmiResult = result.data!!
                )
            )
        }
    }

    fun afterBMIResultsPublished() {
        updateState(
            stateAccess.copy(showAd = false, bmiResult = null)
        )
    }

    private fun getBMIRequest(): BMIRequest {
        return BMIRequest(
            weight = stateAccess.selectedWeight,
            height = stateAccess.selectedHeight,
            gender = stateAccess.selectedGender,

            )
    }


    private fun updateState(stateState: CalculatorUiState) {
        _state.update {
            stateState
        }
    }
}