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
            camera = Camera.open()
            val params = camera!!.parameters
            params.flashMode = Camera.Parameters.FLASH_MODE_OFF
            camera!!.parameters = params
            camera!!.startPreview()
        }
        catch (e: Exception) {
            hasError.value = true
        }
    }

    // call this only after AccessCamera.hasCamera == true
    fun toggleTorch (value: Boolean) {
        camera?.let{
            val params = camera!!.parameters
            if (value) {
                params.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            } else {
                params.flashMode = Camera.Parameters.FLASH_MODE_OFF
            }
            camera!!.parameters = params
        }?: run {
            hasError.value = true
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
                        val duration: Long
                        val toggleState: Boolean
                        when (morseArray[currentIndex]) {
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
                        toggleTorch(toggleState)
                        delay(duration)
                        toggleTorch(false)
                        delay(gapDuration.toLong())
                        currentIndex++
                    }else if (isPause.value) {
                        delay(100)
                    }
                    else if (currentIndex == morseArray.size) {
                        isCompleted.value = true
                        stopCamera()
                    }
                }
            } catch (e: Exception) {
                hasError.value = true
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


















