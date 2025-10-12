package com.example.morse_link.data.repository

import com.example.morse_link.domain.morseRepo.MorseRepository
import javax.inject.Inject

class MorseRepoImpl @Inject constructor(
    private val repository: Repository
): MorseRepository {
    override fun convertToMorse(message: String): String {
        return repository.convertToMorse(message)
    }

    override suspend fun transmitThroFlashlight(morseCode: String) {
        repository.transmitFlashLight(morseCode)
    }

    override fun transmitThroSound(morseCode: String) {
        repository.transmitSound(morseCode)
    }

    override suspend fun transmitThroBoth(morseCode: String) {
        repository.transmitBoth(morseCode)
    }

}