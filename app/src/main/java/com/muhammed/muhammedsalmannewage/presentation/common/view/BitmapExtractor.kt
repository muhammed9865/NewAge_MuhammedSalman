package com.muhammed.muhammedsalmannewage.presentation.common.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

class BitmapExtractor {
    companion object {
        fun of(view: View): Bitmap {
            val bitmap = Bitmap.createBitmap(
                view.width, view.height, Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }
    }
}