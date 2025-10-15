package com.example.morse_link.data.hardware

import android.media.AudioTrack
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
    var indexPlayed = 0

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
        audioTrack: AudioTrack
    ) {
        while (!isCompleted.value) {
            if (!isPaused.value && indexPlayed < bufferShort.size) {
                audioTrack.write(shortArrayOf(bufferShort[indexPlayed]), 0, 1)
                indexPlayed++
            } else if (isPaused.value) {
                delay(100)
            } else if (indexPlayed == bufferShort.size) {
                isCompleted.value = true
                audioTrack.stop()
                audioTrack.release()
                scope.cancel()
            }
        }
    }

    fun startTone(isPause: StateFlow<Boolean>) {

        val audioTrack: AudioTrack = CreateAudioTrack(sampleRate).getAudioTrack()
        var bufferShort = shortArrayOf()

        audioTrack.play()

        for (symbol in morseCode) {
            val tone = when (symbol) {
                '.' -> generateTone(dotDuration)
                '-' -> generateTone(dashDuration)
                else -> generateSilence(spaceDuration)
            }
            bufferShort = bufferShort + tone + generateSilence(gapDuration)
        }

        scope.launch {
                playTone(
                    audioTrack = audioTrack,
                    bufferShort = bufferShort,
                    isPaused = isPause,
                )
        }
    }
}
