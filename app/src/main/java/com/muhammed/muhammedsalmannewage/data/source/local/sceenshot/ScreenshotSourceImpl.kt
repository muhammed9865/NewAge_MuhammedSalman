package com.muhammed.muhammedsalmannewage.data.source.local.sceenshot

import com.muhammed.muhammedsalmannewage.domain.DomainConstants
import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotRequest
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotResponse
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class ScreenshotSourceImpl @Inject constructor() : ScreenshotSource {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun saveScreenshot(screenshotRequest: ScreenshotRequest): State<ScreenshotResponse> {
        // Preparing file
        val screenshotLocation = screenshotRequest.saveLocation + File.separator
        val screenshotsDir = File(screenshotLocation)

        if (!screenshotsDir.exists())
            screenshotsDir.mkdir()

        val imageFile = File.createTempFile(System.currentTimeMillis().toString(),
            SCREENSHOT_NAME,
            screenshotsDir)

        // Trying to save the file and return it's path
        return try {
            val fo = FileOutputStream(imageFile)
            fo.write(screenshotRequest.bytes.toByteArray())
            fo.close()

            // Deletes the file after some time
            deleteFileAfter(imageFile)

            State.Success(
                data = ScreenshotResponse(
                    screenshotPath = imageFile.absolutePath
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
            State.Failure(
                data = null,
                message = "Couldn't save Screenshot",
                throwable = e
            )
        } catch (e: Exception) {
            e.printStackTrace()
            State.Failure(
                data = null,
                message = "Unknown error occurred",
                throwable = e
            )
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun deleteFileAfter(file: File, delay: Long = DomainConstants.SCREENSHOT_DELETE_DELAY) {
        // Using the GlobalScope to make sure that it's not cancelled when the calling coroutine is cancelled
        GlobalScope.launch(Dispatchers.IO) {
            delay(delay)
            file.delete()
            cancel()
        }
    }

    companion object {

        private const val SCREENSHOT_NAME = "screenshot${DomainConstants.SCREENSHOT_EXT}"
    }
}