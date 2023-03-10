package com.muhammed.muhammedsalmannewage.domain.usecase

import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotRequest
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotResponse
import com.muhammed.muhammedsalmannewage.domain.repository.ScreenshotRepository
import javax.inject.Inject

class SaveScreenshotUseCase @Inject constructor(
    private val repository: ScreenshotRepository,
) {

    suspend operator fun invoke(screenshotRequest: ScreenshotRequest) : State<ScreenshotResponse> {
        return repository.saveScreenshot(
            screenshotRequest = screenshotRequest
        )
    }
}