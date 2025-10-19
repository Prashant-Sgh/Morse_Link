package com.example.morse_link.domain.morseRepo

import android.content.Context
import com.example.morse_link.presentation.viewmodels.SharedViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface MorseRepository {
    fun convertToMorse(message: String): String

     fun transmitThroFlashlight(morseCode: String, context: Context, result: (hasError: Boolean) -> Unit, scope: CoroutineScope, isPause: StateFlow<Boolean>, isCanceled: StateFlow<Boolean>)
     fun transmitThroSound(scope: CoroutineScope, morseCode: String, isPause: StateFlow<Boolean>, isCanceled: StateFlow<Boolean>)
     suspend fun transmitThroBoth(morseCode: String)
}