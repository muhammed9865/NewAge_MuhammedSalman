package com.muhammed.muhammedsalmannewage.domain.repository

import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotRequest
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotResponse

interface ScreenshotRepository {

    suspend fun saveScreenshot(
        screenshotRequest: ScreenshotRequest
    ) : State<ScreenshotResponse>
}