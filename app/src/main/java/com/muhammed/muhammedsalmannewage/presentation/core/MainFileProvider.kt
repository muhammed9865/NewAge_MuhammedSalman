package com.muhammed.muhammedsalmannewage.presentation.core

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.muhammed.muhammedsalmannewage.R
import java.io.File

class MainFileProvider : FileProvider(R.xml.provider_paths) {
    companion object {
        fun getUriForLocation(context: Context, location: String): Uri {
            val contentFile = File(location)
            return getUriForFile(context, "${context.packageName}.fileprovider", contentFile)
        }
    }
}
