package com.muhammed.muhammedsalmannewage.data.repository

import com.muhammed.muhammedsalmannewage.data.source.local.sceenshot.ScreenshotSource
import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotRequest
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotResponse
import com.muhammed.muhammedsalmannewage.domain.repository.ScreenshotRepository
import javax.inject.Inject

class ScreenshotRepositoryImpl @Inject constructor(
    private val screenshotSource: ScreenshotSource,
) : ScreenshotRepository {

    override suspend fun saveScreenshot(screenshotRequest: ScreenshotRequest): State<ScreenshotResponse> {
        return screenshotSource.saveScreenshot(
           screenshotRequest = screenshotRequest
        )
    }
}