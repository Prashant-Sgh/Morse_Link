package com.example.morse_link.domain.morseRepo

interface MorseRepository {
    fun convertToMorse(message: String): String

     suspend fun transmitThroFlashlight(morseCode: String)
     suspend fun transmitThroSound(morseCode: String)
     suspend fun transmitThroBoth(morseCode: String)
}