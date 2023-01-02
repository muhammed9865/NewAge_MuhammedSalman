package com.muhammed.muhammedsalmannewage.data.source.local.sceenshot

import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotRequest
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotResponse
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class ScreenshotSourceImpl @Inject constructor() : ScreenshotSource {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun saveScreenshot(screenshotRequest: ScreenshotRequest): State<ScreenshotResponse> {
        // Preparing file
        val fileLocation = screenshotRequest.saveLocation + File.separator + "temp_screenshot.jpeg"
        val file = File(fileLocation)

        // Trying to save the file and return it's path
        return try {
            file.createNewFile()
            val fo = FileOutputStream(file)
            fo.write(screenshotRequest.bytes.toByteArray())
            State.Success(
                data = ScreenshotResponse(
                    screenshotPath = fileLocation
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
            State.Failure(
                data = null,
                message = "Couldn't save Screenshot",
                throwable = e
            )
        }
    }
}