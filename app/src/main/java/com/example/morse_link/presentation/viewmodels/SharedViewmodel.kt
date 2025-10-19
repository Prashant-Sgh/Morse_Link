package com.example.morse_link.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morse_link.data.hardware.ToneGenerator
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

    private val _isPause = MutableStateFlow(false)
    val isPause: StateFlow<Boolean> = _isPause

    private val _isTransmissionCanceled = MutableStateFlow(false)
    val isTransmissionCanceled: StateFlow<Boolean> = _isTransmissionCanceled

    private val _isLightEnabled = MutableStateFlow(true)
    val  isLightEnabled: StateFlow<Boolean> = _isLightEnabled


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

    fun UpdatePauseStatus(state: Boolean) {
        _isPause.value = state
    }

    fun CancelTransmission(value: Boolean) {
        _isTransmissionCanceled.value = value
    }

    fun toggleLightEnabled (isEnabled: Boolean) {
        _isLightEnabled.value = isEnabled
    }

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun TransmitFlashlight(morseCode: String, context: Context, result: (hasError: Boolean) -> Unit) {
        repository.transmitThroFlashlight(morseCode, context, result, scope, isPause, isTransmissionCanceled )
    }

    fun TransmitSound(morseCode: String) {
        _isPause.value = false
        _isTransmissionCanceled.value = false
        repository.transmitThroSound(scope = scope, morseCode, isPause, isTransmissionCanceled)
    }

    suspend fun TransmitBoth(morseCode: String) {
        repository.transmitThroBoth(morseCode)
    }

}