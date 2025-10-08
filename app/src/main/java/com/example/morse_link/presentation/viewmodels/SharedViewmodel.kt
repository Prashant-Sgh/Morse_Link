package com.example.morse_link.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.morse_link.data.repository.Repository
import com.example.morse_link.domain.morseRepo.MorseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewmodel @Inject constructor(
    private val repository: MorseRepository
): ViewModel()
{
    private val _messageString = MutableStateFlow("")
    val messageString: StateFlow<String> = _messageString

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing

    private val _morseCode = MutableStateFlow("")
    val morseCode: StateFlow<String> = _morseCode

    fun UpdateMessage(message: String) {
        _messageString.value = message
    }

    fun ConvertToMorse(message: String) {
        _morseCode.value = repository.convertToMorse(message)

    }

    fun UpdateProcessingState(state: Boolean) {
        _isProcessing.value = state
    }

    suspend fun TransmitFlashlight(morseCode: String) {
        repository.transmitThroFlashlight(morseCode)
    }

    suspend fun TransmitSound(morseCode: String) {
        repository.transmitThroSound(morseCode)
    }

    suspend fun TransmitBoth(morseCode: String) {
        repository.transmitThroBoth(morseCode)
    }

}