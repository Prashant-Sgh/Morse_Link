package com.example.morse_link.data.hardware

import android.hardware.Camera
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay
import kotlin.time.Duration

class FlashlightController {

    var camera: Camera? = null

    val hasException = mutableStateOf(false)

    val dotDuration = 80
    val dashDuration = dotDuration * 3
    val gapDuration = dotDuration
    val spaceDuration = dotDuration * 7

    // call this only after AccessCamera.hasCamera == true
    fun turnOnTorch () {
        try {
            camera = Camera.open()
            val params = camera!!.parameters
            params.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            camera!!.parameters = params
            camera!!.startPreview()
        }
        catch (e: Exception) {
            hasException.value = true
        }
    }

    fun turnOffTorch () {
        try {
            camera?.let {
                val params = it.parameters
                params.flashMode = Camera.Parameters.FLASH_MODE_OFF
                it.parameters = params
                it.stopPreview()
                it.release()
                camera = null
            }
        }
        catch (e: Exception) {
            hasException.value = true
        }
    }

    suspend fun toggleTorch (value: Boolean, duration: Long) {
        if (value) {
            turnOnTorch()
        }
        else{
            turnOffTorch()
        }
        delay(duration)
    }

    var data = 0
    suspend fun startLight(morseCode: String) {
        for (code in morseCode) {
            val duration: Long
            val toggleState: Boolean
            val value = when (code) {
                '.' -> {
                    toggleState = true
                    duration = dotDuration.toLong()
                }
                '-' -> {
                    toggleState = true
                    duration = dashDuration.toLong()
                }
                else -> {
                    toggleState = false
                    duration = spaceDuration.toLong()
                }
            }
            toggleTorch(value = toggleState, duration)
        }
    }

}


















