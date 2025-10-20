package com.example.morse_link.data.hardware

import android.media.AudioTrack
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.sin

class ToneGenerator (private val scope: CoroutineScope, private val morseCode: String) {

    val sampleRate = 48000
    val frequency = 800
    val dotDuration = 80
    val dashDuration = dotDuration * 3
    val gapDuration = dotDuration
    val spaceDuration = dotDuration * 7
    val isCompleted = mutableStateOf(false)

    fun generateTone(duration: Int): ShortArray {
        val audioSlices = ( duration / 1000.0 * sampleRate ).toInt()
        val audioBuffer = ShortArray(audioSlices)
        for (i in audioBuffer.indices) {
            val angle = 2.0 * Math.PI * i * frequency / sampleRate
            audioBuffer[i] = ( sin(angle) * Short.MAX_VALUE ).toInt().toShort()
        }
        return audioBuffer
    }
    fun generateSilence( duration: Int): ShortArray {
        val slices = (  duration / 1000.0  * sampleRate ).toInt()
        return ShortArray(slices) { 0 }
    }
    suspend fun playTone(
        bufferShort: ShortArray,
        isPaused: StateFlow<Boolean>,
        audioTrack: AudioTrack,
        isCanceled: StateFlow<Boolean>
    ) {
        Log.d("secondTransmission", "playTone() - playTone called and started")

        Log.d("secondTransmission", "playTone() - isCompleted State - ${isCompleted.value}")
        Log.d("secondTransmission", "playTone() - Morse Message - $morseCode")
        var indexPlayed = 0
        while (!isCompleted.value) {
            if (isCanceled.value) {
                isCompleted.value = true
                releaseAudioTrack()
                scope.cancel()
            }
            if (!isPaused.value && indexPlayed < bufferShort.size) {
                audioTrack.write(shortArrayOf(bufferShort[indexPlayed]), 0, 1)
                indexPlayed++
            } else if (isPaused.value) {
                delay(100)
            } else if (indexPlayed == bufferShort.size) {
                isCompleted.value = true
                releaseAudioTrack()
                scope.cancel()
            }
        }
    }

    val audioTrack: AudioTrack = CreateAudioTrack(sampleRate).getAudioTrack()
    fun startTone(isPause: StateFlow<Boolean>, isCanceled: StateFlow<Boolean>) {
        var bufferShort = shortArrayOf()

        Log.d("secondTransmission", "startTone() - isCompleted State - ${isCompleted.value}")
        Log.d("secondTransmission", "startTone() - Morse Message - $morseCode")

        audioTrack.play()

        Log.d("secondTransmission", "startTone() - audioTrack.play() - DONE")

        for (symbol in morseCode) {
            val tone = when (symbol) {
                '.' -> generateTone(dotDuration)
                '-' -> generateTone(dashDuration)
                else -> generateSilence(spaceDuration)
            }
            bufferShort = bufferShort + tone + generateSilence(gapDuration)
        }

        Log.d("secondTransmission", "startTone() - bufferShort created- DONE")

        try {
            Log.d("secondTransmission", "scope - try block begins.")
            scope.launch {
                Log.d("secondTransmission", "startTone() - scope launched")
                playTone(
                    audioTrack = audioTrack,
                    bufferShort = bufferShort,
                    isPaused = isPause,
                    isCanceled = isCanceled
                )
                Log.d("secondTransmission", "startTone() - scope launched & playTone() finished.")
            }
            Log.d("secondTransmission", "scope - try block DONE.")
        }
        catch (e: Exception) {
            Log.d("secondTransmission", "scope - catch block begins.")
            Log.d("secondTransmission", "scopeError - scope not launched with error: $e")
        }
    }

    fun releaseAudioTrack() {
        audioTrack.stop()
        audioTrack.release()
    }

}
