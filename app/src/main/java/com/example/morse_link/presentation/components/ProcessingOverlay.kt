package com.example.morse_link.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlin.random.Random

//@Preview (showBackground = true, showSystemUi = true)
@Composable
fun ProcessingOverlay(isProcessing: Boolean) {
    if (isProcessing) {
        Box(
            Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            val randomLoader = Random.nextInt(1,9)
            val composition by rememberLottieComposition(
                LottieCompositionSpec.Asset("LOADER-5.json")
            )
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = 5,
                isPlaying = true,
                restartOnPlay = true
            )
            LottieAnimation(
                composition,
                progress = { progress },
                modifier = Modifier.width(if (randomLoader == 8)  (300.dp) else (150.dp))
            )
        }
    }
}