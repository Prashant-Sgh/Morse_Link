package com.example.morse_link.data.hardware

import android.media.AudioTrack
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
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

    fun playTone(
        bufferShort: ShortArray,
        isPaused: Boolean,
        currentIndex: Int,
        updateCurrentIndex: (indexPlayed: Int) -> Unit,
        audioTrack: AudioTrack
    ) {
        var indexPlayed = 0
        if (!isPaused) {
            audioTrack.play()
            audioTrack.write(generateSilence(1000), 0, (1000 * sampleRate) / 1000 )
            for (x in 0 until bufferShort.size) {
                audioTrack.write(shortArrayOf(bufferShort[x]), 0, 1)
                indexPlayed++
            }
        }
        else{
            updateCurrentIndex(indexPlayed)
            audioTrack.stop()
        }
        if(currentIndex == bufferShort.size - 1 ) {
            isCompleted.value = true
            audioTrack.stop()
            audioTrack.release()
            scope.cancel()
        }
    }


    fun startTone(isPause: StateFlow<Boolean>) {

        val audioTrack: AudioTrack = CreateAudioTrack(sampleRate).getAudioTrack()
        val currentIndex = mutableStateOf(0)

        scope.launch {
            var bufferShort = shortArrayOf()
            audioTrack.play()
            for ( symbol in morseCode ) {
                val tone = when (symbol) {
                    '.' -> generateTone(dotDuration)
                    '-' -> generateTone(dashDuration)
                    else -> generateSilence(spaceDuration)
                }
//                audioTrack.write(tone, 0 , tone.size)
//                audioTrack.write(generateSilence(200), 0, generateSilence(200).size)
//                bufferShort.addAll(tone.toList())
//                bufferShort.addAll(generateSilence(gapDuration).toList())
                bufferShort = bufferShort + tone + generateSilence(gapDuration)
            }

//            audioTrack.write(bufferShort, 0, bufferShort.size)

            while(!isCompleted.value){
                playTone(
                    audioTrack = audioTrack,
                    bufferShort = bufferShort,
                    isPaused = isPause.value,
                    currentIndex = currentIndex.value,
                    updateCurrentIndex = { currentIndex.value = it }
                )
            }
        }
    }

}