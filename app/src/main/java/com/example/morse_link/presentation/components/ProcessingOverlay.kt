package com.example.morse_link.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlin.random.Random

@Composable
fun ProcessingOverlay(
    onComplete: () -> Unit,
    onCancel: () -> Unit
) {
        Box(
            Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            val randomLoader = Random.nextInt(1,8)
            val lottieAnimatable = rememberLottieAnimatable()
            val composition by rememberLottieComposition(
                LottieCompositionSpec.Asset("LOADER-$randomLoader.json")
            )

            LottieAnimation(
                composition,
                progress = { lottieAnimatable.progress },
                modifier = Modifier.width(if (randomLoader == 8)  (300.dp) else (150.dp)),
            )

            LaunchedEffect(composition) {
                if (composition != null) {
                    lottieAnimatable.animate(
                        composition = composition,
                        iterations = 1
                    )
                    onComplete()
                }
            }

            Column {
                Spacer(modifier = Modifier.weight(0.8f))
                Text(
                    "Cancel",
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.clickable(
                        enabled = true,
                        onClick = {
                            onCancel()
                        }
                    )
                )
                Spacer(modifier = Modifier.weight(0.2f))
            }

        }
}