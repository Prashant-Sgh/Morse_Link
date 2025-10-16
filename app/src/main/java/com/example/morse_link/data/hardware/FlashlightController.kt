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


    fun startPreview () {
        camera = Camera.open()
        val params = camera!!.parameters
        params.flashMode = Camera.Parameters.FLASH_MODE_OFF
        camera!!.parameters = params
        camera!!.startPreview()
    }

    // call this only after AccessCamera.hasCamera == true
    fun toggleTorch (value: Boolean) {
        val params = camera!!.parameters

        params.flashMode =  if (value) {
            Camera.Parameters.FLASH_MODE_TORCH
        }
        else{
            Camera.Parameters.FLASH_MODE_OFF
        }
        camera!!.parameters = params
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
            toggleTorch(value = toggleState)
            delay(duration + gapDuration.toLong())
        }
        stopCamera()
    }

    fun stopCamera () {
        camera?.apply {
            stopPreview()
            release()
        }
        camera = null
    }

}


















