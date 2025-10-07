package com.example.morse_link.domain.morseRepo

interface MorseRepository {
    fun convertToMorse(message: String): String

     suspend fun transmitThroughFlashlight(morseCode: String)
     suspend fun transmitThroughSound(morseCode: String)
}