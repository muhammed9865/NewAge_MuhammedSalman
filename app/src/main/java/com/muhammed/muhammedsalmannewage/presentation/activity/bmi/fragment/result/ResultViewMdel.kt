package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment.result

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.lifecycle.ViewModel
import com.muhammed.muhammedsalmannewage.presentation.common.view.BitmapExtractor
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(): ViewModel() {

    fun takeScreenshot(view: View, saveLocation: String) : Intent? {
        val bitmap = BitmapExtractor.of(view)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"

        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        val fileLocation = saveLocation + File.separator + "temp_screenshot.jpeg"
        val file = File(fileLocation)
        try {
            file.createNewFile()
            val fo = FileOutputStream(file)
            fo.write(bytes.toByteArray())
        }catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(fileLocation))
        return intent

    }
}