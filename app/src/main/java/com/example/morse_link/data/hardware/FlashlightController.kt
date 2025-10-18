package com.example.morse_link.data.hardware

import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import java.util.jar.Manifest
import kotlin.time.Duration

@Suppress("Deprication")
class FlashlightController {

    var camera: Camera? = null

    val dotDuration = 80
    val dashDuration = dotDuration * 3
    val gapDuration = dotDuration
    val spaceDuration = dotDuration * 7

    val hasError = mutableStateOf(false)


    fun startPreview () {
        try {
            Log.d("cameraError", "startPreview - Camera.open() STARTING")
            camera = Camera.open()
            Log.d("cameraError", "startPreview - Camera.open() DONE")
            val params = camera!!.parameters
            Log.d("cameraError", "startPreview - camera!!.preview")
            params.flashMode = Camera.Parameters.FLASH_MODE_OFF
            Log.d("cameraError", "startPreview - params.flashMode")
            camera!!.parameters = params
            Log.d("cameraError", "startPreview - camera!!.parameters")
            camera!!.startPreview()
            Log.d("cameraError", "startPreview - camera!!.startPreview")
        }
        catch (e: Exception) {
            hasError.value = true
            Log.d("cameraError", "ERROR - the error is: $e")
        }
    }

    // call this only after AccessCamera.hasCamera == true
    fun toggleTorch (value: Boolean) {
        camera?.let{
            val params = camera!!.parameters
            if (value) {
                params.flashMode = Camera.Parameters.FLASH_MODE_TORCH
                Log.d("cameraError", "Flash - ON")
            } else {
                params.flashMode = Camera.Parameters.FLASH_MODE_OFF
                Log.d("cameraError", "Flash - OFF")
            }
            camera!!.parameters = params
        }?: run {
            hasError.value = true
            Log.d("cameraError", "ERROR - toggleTorch - the camera class is null.")
        }
    }

    suspend fun startLight(morseCode: String) {
        if (!hasError.value) {
            try {
                Log.d("cameraError", "startLight() - Getting started to transmit light signals")
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
                    delay(duration)
                    toggleTorch(false)
                    delay(gapDuration.toLong())
                }
            } catch (e: Exception) {
                hasError.value = true
                Log.d("cameraError", "ERROR - startLight function fails, the error was - $e")
            } finally {
                stopCamera()
            }
        }
    }

    fun stopCamera () {
        camera?.apply {
            stopPreview()
            release()
        }
        camera = null
    }

}


















