package com.example.morse_link.data.repository

import android.content.Context
import com.example.morse_link.domain.morseRepo.MorseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MorseRepoImpl @Inject constructor(
    private val repository: Repository
): MorseRepository {
    override fun convertToMorse(message: String): String {
        return repository.convertToMorse(message)
    }

    override fun transmitThroFlashlight(morseCode: String, context: Context, result: (hasError: Boolean) -> Unit, scope: CoroutineScope) {
        repository.transmitFlashLight(morseCode, context, result, scope)
    }

    override fun transmitThroSound(scope: CoroutineScope, morseCode: String, isPause: StateFlow<Boolean>, isCanceled: StateFlow<Boolean>) {
        repository.transmitSound(scope, morseCode, isPause, isCanceled)
    }

    override suspend fun transmitThroBoth(morseCode: String) {
        repository.transmitBoth(morseCode)
    }

}