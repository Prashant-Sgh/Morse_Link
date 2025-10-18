package com.example.morse_link.data.hardware

import android.content.Context
import android.content.pm.PackageManager

class AccessCamera {

    fun hasCamera (context: Context): Boolean {
        val pm = context.packageManager
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) && pm.hasSystemFeature(
            PackageManager.FEATURE_CAMERA_FLASH)
    }

}