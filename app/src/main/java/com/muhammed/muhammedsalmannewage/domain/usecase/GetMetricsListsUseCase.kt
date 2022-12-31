package com.muhammed.muhammedsalmannewage.domain.usecase

import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.bmi.Gender
import com.muhammed.muhammedsalmannewage.domain.model.bmi.MetricsLists
import javax.inject.Inject

/*
    * The reason for this UseCase is that if ever the app needs to scale,
    * And fetch lists from different datasource,
    * No logic need to be done in the presentation layer
 */
class GetMetricsListsUseCase @Inject constructor() {
    operator fun invoke() : State<MetricsLists> {
        return State.Success(
            data = MetricsLists(
                weightList = getWeights(),
                heightList = getHeights(),
                genderList = getGenders()
            )
        )
    }

    private fun getWeights() = List(size = WEIGHTS_COUNT) { weight ->
        weight + 1
    }

    private fun getHeights() = List(size = HEIGHTS_COUNT) { weight ->
        weight + 1
    }

    private fun getGenders() = listOf(
        Gender.MALE, Gender.FEMALE
    )


    companion object {
        private const val WEIGHTS_COUNT = 250
        private const val HEIGHTS_COUNT = 250
    }
}