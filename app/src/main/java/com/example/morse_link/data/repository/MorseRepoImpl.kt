package com.example.morse_link.data.repository

import com.example.morse_link.domain.morseRepo.MorseRepository
import javax.inject.Inject

class MorseRepoImpl @Inject constructor(
    private val repository: Repository
): MorseRepository {
    override fun convertToMorse(message: String): String {
        return repository.convertToMorse(message)
    }

    override suspend fun transmitThroughFlashlight(morseCode: String) {
        repository.transmitFlashLight(morseCode)
    }

    override suspend fun transmitThroughSound(morseCode: String) {
        repository.transmitSound(morseCode)
    }

}