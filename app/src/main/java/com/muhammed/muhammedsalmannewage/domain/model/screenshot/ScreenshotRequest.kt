package com.muhammed.muhammedsalmannewage.domain.model.screenshot

import java.io.ByteArrayOutputStream

data class ScreenshotRequest(
    val bytes: ByteArrayOutputStream,
    val saveLocation: String
)
