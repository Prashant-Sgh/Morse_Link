package com.example.morse_link.data.hardware

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.math.sin

class ToneGenerator {

    fun generateTone(morseCode: String) {
//        import android.media.AudioFormat
//                import android.media.AudioManager
//                import android.media.AudioTrack
//                import kotlin.math.sin
//
//        fun transmitMorseCode(morseCode: String) {
//            val sampleRate = 44100
//            val frequency = 600.0
//            val dotDurationMs = 100 // 100ms for dot
//            val dashDurationMs = dotDurationMs * 3
//            val gapDurationMs = dotDurationMs // gap between symbols
//
//            fun generateTone(durationMs: Int): ShortArray {
//                val numSamples = (durationMs / 1000.0 * sampleRate).toInt()
//                val samples = ShortArray(numSamples)
//                for (i in samples.indices) {
//                    val angle = 2.0 * Math.PI * i * frequency / sampleRate
//                    samples[i] = (sin(angle) * Short.MAX_VALUE).toInt().toShort()
//                }
//                return samples
//            }
//
//            fun generateSilence(durationMs: Int): ShortArray {
//                val numSamples = (durationMs / 1000.0 * sampleRate).toInt()
//                return ShortArray(numSamples) { 0 }
//            }
//
//            val audioTrack = AudioTrack(
//                AudioManager.STREAM_MUSIC,
//                sampleRate,
//                AudioFormat.CHANNEL_OUT_MONO,
//                AudioFormat.ENCODING_PCM_16BIT,
//                sampleRate,
//                AudioTrack.MODE_STREAM
//            )
//
//            audioTrack.play()
//
//            for (symbol in morseCode) {
//                val tone = when (symbol) {
//                    '.' -> generateTone(dotDurationMs)
//                    '-' -> generateTone(dashDurationMs)
//                    else -> generateSilence(gapDurationMs)
//                }
//                audioTrack.write(tone, 0, tone.size)
//                audioTrack.write(generateSilence(gapDurationMs), 0, gapDurationMs * sampleRate / 1000)
//            }
//
//            audioTrack.stop()
//            audioTrack.release()
//        }

        val sampleRate = 48000
        val frequency = 800
        val dotDuration = 80
        val dashDuration = dotDuration * 3
        val gapDuration = dotDuration
        val spaceDuration = dotDuration * 7

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

        val audioTrack: AudioTrack

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For API 23+ I can use builder
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            val format = AudioFormat.Builder()
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setSampleRate(sampleRate)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build()

            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(audioAttributes)
                .setAudioFormat(format)
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()
        }
        else {
            // In case API < 23, I need to use the deprecated one
            @Suppress("DEPRECATION")
            audioTrack = AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                sampleRate,
                AudioTrack.MODE_STREAM
            )
        }

        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

        scope.launch {
            audioTrack.play()
            audioTrack.write(generateSilence(2000), 0, (2000 * sampleRate) / 1000 )
            for ( symbol in morseCode ) {
                val tone = when (symbol) {
                    '.' -> generateTone(dotDuration)
                    '-' -> generateTone(dashDuration)
                    else -> generateSilence(spaceDuration)
                }
                audioTrack.write(tone, 0, tone.size)
                audioTrack.write(generateSilence(gapDuration), 0, (gapDuration * sampleRate) / 1000 )
            }
            audioTrack.stop()
            audioTrack.release()
        }

//        scope.cancel()
//        audioTrack.stop()
//        audioTrack.release()

    }

}