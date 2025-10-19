package com.example.morse_link.data.repository

import android.content.Context
import android.os.Build
import com.example.morse_link.data.hardware.AccessCamera
import com.example.morse_link.data.hardware.FlashlightController
import com.example.morse_link.data.hardware.ToneGenerator
import com.example.morse_link.data.model.MorseMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



class Repository @Inject constructor
    (
    val morseMap: MorseMap,
//    val viewmodel: SharedViewmodel
    ) {

    fun convertToMorse(message: String): String {
        val morseCodeMap = morseMap.morseCodeMap
        val morseCode = message.uppercase().map { char ->
            morseCodeMap[char]?: "*"
        }.joinToString(" ")
        return morseCode
    }

    fun transmitFlashLight(morseCode: String, context: Context, result: (hasError: Boolean) -> Unit, scope: CoroutineScope, isPause: StateFlow<Boolean>, isCanceled: StateFlow<Boolean>) {
        scope.launch(Dispatchers.IO){
            if (AccessCamera().hasCamera(context)) {
//                Log.d("cameraError", "The device has camera and flash mode")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    Log.d("cameraError", "And the device is API 23+")
                    val flashLightController = FlashlightController()
//                    Log.d("cameraError", "FlashLightController() initiated")
                    flashLightController.startPreview()
//                    Log.d("cameraError", "startPreview DONE")
                    flashLightController.startLight(morseCode = morseCode, isPause = isPause, isCanceled = isCanceled)
//                    Log.d("cameraError", "startLight DONE")
                    result(flashLightController.hasError.value)
//                    Log.d("cameraError", "value set for hasError to: ${flashLightController.hasError.value}")
                }
            } else {
                result(true)
            }
        }
    }

    fun transmitSound(scope: CoroutineScope, morseCode: String, isPause: StateFlow<Boolean>, isCanceled: StateFlow<Boolean>) {
        ToneGenerator(
            scope = scope,
            morseCode = morseCode,
            )
            .startTone(isPause = isPause, isCanceled = isCanceled)
    }

    suspend fun transmitBoth(morseCode: String) {
        /*TODO - implement Both transmit*/
    }

}