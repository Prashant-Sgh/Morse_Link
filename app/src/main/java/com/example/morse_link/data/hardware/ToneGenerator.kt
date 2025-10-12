package com.example.morse_link.data.hardware

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
    }

}