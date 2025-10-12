package com.example.morse_link.domain.morseRepo

interface MorseRepository {
    fun convertToMorse(message: String): String

     suspend fun transmitThroFlashlight(morseCode: String)
     fun transmitThroSound(morseCode: String)
     suspend fun transmitThroBoth(morseCode: String)
}