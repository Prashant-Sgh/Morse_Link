package com.example.morse_link.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.morse_link.data.repository.Repository
import com.example.morse_link.domain.morseRepo.MorseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _isPause = MutableStateFlow(false)
    val isPause: StateFlow<Boolean> = _isPause

    private val _isResume = MutableStateFlow(false)
    val isResume: StateFlow<Boolean> = _isResume


    fun UpdateMessage(message: String) {
        _messageString.value = message
    }

    fun ConvertToMorse(message: String) {
        _morseCode.value = repository.convertToMorse(message)

    }

    fun UpdateProcessingState(state: Boolean) {
        _isProcessing.value = state
    }

    fun toggleTonePlayStatus() {
        _isPause.value = !_isPause.value
    }

    suspend fun TransmitFlashlight(morseCode: String) {
        repository.transmitThroFlashlight(morseCode)
    }

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    fun TransmitSound(morseCode: String, isPaused: StateFlow<Boolean>) {
        repository.transmitThroSound(scope = scope, morseCode, isPaused)
    }

    suspend fun TransmitBoth(morseCode: String) {
        repository.transmitThroBoth(morseCode)
    }

}