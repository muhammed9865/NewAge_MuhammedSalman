package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment.result

import android.content.Intent

data class ResultUiState(
    val error: String? = null,
    val shareIntent: Intent? = null,
    val rateIntent: Intent? = null,
    // Used if the rateIntent causes an except (ActivityNotFound)
    val rateIntentSecondary: Intent? = null,
    val showAds: Boolean = false,
)
