package com.example.morse_link.data.repository

import com.example.morse_link.data.hardware.ToneGenerator
import com.example.morse_link.data.model.MorseMap
import javax.inject.Inject



class Repository @Inject constructor
    (
    val morseMap: MorseMap,
    ) {

    fun convertToMorse(message: String): String {
        val morseCodeMap = morseMap.morseCodeMap
        val morseCode = message.uppercase().map { char ->
            morseCodeMap[char]?: "*"
        }.joinToString(" ")
        return morseCode
    }

    suspend fun transmitFlashLight(morseCode: String) {
        /*TODO - implement flashlight transmit*/
    }


    fun transmitSound(morseCode: String) {
        ToneGenerator().generateTone(morseCode)
    }

    suspend fun transmitBoth(morseCode: String) {
        /*TODO - implement Both transmit*/
    }

}