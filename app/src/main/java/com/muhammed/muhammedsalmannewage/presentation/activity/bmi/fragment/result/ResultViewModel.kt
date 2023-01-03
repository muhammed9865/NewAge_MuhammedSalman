package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment.result

import android.content.ClipData
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMIResult
import com.muhammed.muhammedsalmannewage.domain.model.screenshot.ScreenshotRequest
import com.muhammed.muhammedsalmannewage.domain.usecase.SaveScreenshotUseCase
import com.muhammed.muhammedsalmannewage.presentation.core.MainFileProvider
import com.muhammed.muhammedsalmannewage.presentation.core.common.view.BitmapExtractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

private const val TAG = "ResultViewModel"
@HiltViewModel
class ResultViewModel @Inject constructor(
    private val saveScreenshotUseCase: SaveScreenshotUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ResultUiState())
    val state = _state.asStateFlow()
    private val stateAccess get() = state.value

    fun takeScreenshot(view: View, saveLocation: String) {
        viewModelScope.launch(Dispatchers.IO) {

            // Ran on Main thread because of BitmapExtractor is accessing a view
            // Accessing a view in different threads causes a crash
            val bitmap = async(Dispatchers.Main) { BitmapExtractor.of(view) }

            // Compress and save the Bitmap to bytes
            val bytes = ByteArrayOutputStream()
            bitmap.await().compress(Bitmap.CompressFormat.PNG, 100, bytes)

            val screenshotRequest = ScreenshotRequest(
                bytes = bytes,
                saveLocation = "$saveLocation/screenshots/"
            )

            val saveResponse = saveScreenshotUseCase.invoke(
                screenshotRequest = screenshotRequest
            )
            // If the screenshot is saved, construct an Intent to share it.
            if (saveResponse.isSuccessful()) {
                val uri = MainFileProvider.getUriOForLocation(
                    context = view.context,
                    location = saveResponse.data!!.screenshotPath
                )

                val shareIntent = provideShareIntent(uri)

                setState(
                    stateAccess.copy(shareIntent = shareIntent)
                )
                // Delaying before resetting to make sure the state is handled already
                delay(200)
                // Setting the intent to null, so it doesn't launch again on configuration change
                setState(
                    stateAccess.copy(shareIntent = null)
                )
            } else {
                setState(
                    stateAccess.copy(error = "Error taking screenshot", shareIntent = null)
                )
            }
        }

    }

    private fun provideShareIntent(uri: Uri): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setDataAndType(uri, "image/png")
        shareIntent.clipData = ClipData(
            ClipData.newRawUri("", uri)
        )

        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        return shareIntent
    }

    // Constructing a Rating intent to rate app
    // Two intents are constructed
    // if main (rateIntent) causes NoActivityFoundException
    // the rateIntentSecondary one should be used
    fun rateApp(packageName: String) {
        val uri = Uri.parse("market://details?id=$packageName")
        // Making sure only the Play Store who's targeted by the above Uri
        val playStorePackageName = "com.android.vending"
        val rateIntent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage(playStorePackageName)
        }

        val uriSecondary = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        val rateIntentSecondary = Intent(Intent.ACTION_VIEW, uriSecondary)

        viewModelScope.launch {
            setState(
                stateAccess.copy(
                    rateIntent = rateIntent,
                    rateIntentSecondary = rateIntentSecondary
                )
            )
            // Delaying before resetting to make sure the state is handled already
            delay(200)
            // Setting the intent to null, so it doesn't launch again on configuration change
            setState(
                stateAccess.copy(
                    rateIntent = null,
                    rateIntentSecondary = null
                )
            )
        }

    }

    private fun setState(state: ResultUiState) {
        _state.update {
            state
        }
    }

    fun onAdFailed() {
        setState(
            stateAccess.copy(showAds = false)
        )
    }

    fun onAdLoaded() {
        setState(
            stateAccess.copy(showAds = true)
        )
    }

    fun onBMIResult(name: String?, bmiResult: BMIResult) {
        setState(
            stateAccess.copy(name = name ?: "there", bmiResult = bmiResult)
        )
    }


}