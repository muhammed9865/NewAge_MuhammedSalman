package com.muhammed.muhammedsalmannewage.data.repository

import com.muhammed.muhammedsalmannewage.data.source.local.sceenshot.ScreenshotSource
import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotRequest
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotResponse
import com.muhammed.muhammedsalmannewage.domain.repository.BMIRepository
import javax.inject.Inject

class BMIRepositoryImpl @Inject constructor(
    private val screenshotSource: ScreenshotSource,
) : BMIRepository {

    override suspend fun saveScreenshot(screenshotRequest: ScreenshotRequest): State<ScreenshotResponse> {
        return screenshotSource.saveScreenshot(
           screenshotRequest = screenshotRequest
        )
    }
}