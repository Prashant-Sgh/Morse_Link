package com.example.morse_link.data.hardware

import android.hardware.Camera
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow

@Suppress("Deprication")
class FlashlightController {

    var camera: Camera? = null

    val isCompleted = mutableStateOf(false)
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

    var currentIndex = 0
    suspend fun startLight(
        morseCode: String,
        isPause: StateFlow<Boolean>,
        isCanceled: StateFlow<Boolean>
    ) {
        if (!hasError.value) {
            val morseArray = morseCode.toCharArray()
            try {
                while (!isCompleted.value) {
                    if (isCanceled.value) {
                        isCompleted.value = true
                        stopCamera()
                    }
                    if (!isPause.value && currentIndex < morseArray.size) {
                        for (index in currentIndex..morseArray.size - 1) {
                            val duration: Long
                            val toggleState: Boolean
                            when (morseArray[index]) {
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
                            currentIndex++
                        }
                    }else if (currentIndex == morseArray.size) {
                        isCompleted.value = true
                        stopCamera()
                    }

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


















