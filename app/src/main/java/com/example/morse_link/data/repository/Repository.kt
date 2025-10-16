package com.example.morse_link.data.repository

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.morse_link.data.hardware.AccessCamera
import com.example.morse_link.data.hardware.FlashlightController
import com.example.morse_link.data.hardware.ToneGenerator
import com.example.morse_link.data.model.MorseMap
import com.example.morse_link.presentation.viewmodels.SharedViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

    fun transmitFlashLight(morseCode: String, context: Context, result: (hasError: Boolean) -> Unit, scope: CoroutineScope) {
        /*TODO - implement flashlight transmit*/
        scope.launch{
            if (AccessCamera().hasCamera(context)) {
                val flashLightController = FlashlightController()
                flashLightController.startPreview()
                flashLightController.startLight(morseCode)
                result(false)
            } else {
                result(true)
            }
        }
    }

    fun transmitSound(scope: CoroutineScope, morseCode: String, isPause: StateFlow<Boolean>) {
//        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        ToneGenerator(
            scope = scope,
            morseCode = morseCode,
            )
            .startTone(isPause = isPause)
    }

    suspend fun transmitBoth(morseCode: String) {
        /*TODO - implement Both transmit*/
    }

}