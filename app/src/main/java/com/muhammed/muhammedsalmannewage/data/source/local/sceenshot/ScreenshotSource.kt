package com.muhammed.muhammedsalmannewage.data.source.local.sceenshot

import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotRequest
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotResponse

interface ScreenshotSource {

    suspend fun saveScreenshot(screenshotRequest: ScreenshotRequest) : State<ScreenshotResponse>

}