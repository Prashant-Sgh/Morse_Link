package com.example.morse_link.domain.morseRepo

import com.example.morse_link.presentation.viewmodels.SharedViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface MorseRepository {
    fun convertToMorse(message: String): String

     suspend fun transmitThroFlashlight(morseCode: String)
     fun transmitThroSound(scope: CoroutineScope, morseCode: String, isPause: StateFlow<Boolean>)
     suspend fun transmitThroBoth(morseCode: String)
}