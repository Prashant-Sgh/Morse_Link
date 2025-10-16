package com.example.morse_link.data.hardware

import android.content.Context
import android.content.pm.PackageManager

class AccessCamera {

    fun hasCamera (context: Context): Boolean {
        if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true
        }
        else {
            return false
        }
    }

}