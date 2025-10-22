package com.example.morse_link.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.morse_link.presentation.navigation.Screens

@Composable
fun TransmitScreen(
    navController: NavHostController,
    onStateChange: () -> Unit,
    onTransmissionCanceled: () -> Unit
) {

    var isTransmitting by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 46.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Morse code is ready",
                fontSize = 23.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 46.dp, end = 46.dp, top = 100.dp, bottom = 40.dp)
            )

            Spacer(Modifier.weight(0.35f))

            Box(
                modifier = Modifier
                    .background(
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(
                            size = 100.dp
                        )
                    )
                    .size(180.dp)
                    .clickable(
                        enabled = true,
                        onClick = {
                            isTransmitting = !isTransmitting
                            onStateChange()
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isTransmitting) "PAUSE" else "RESUME",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 2.sp
                )
            }

            Spacer(Modifier.weight(0.5f))

            OutlinedButton(
                onClick = {
                    onTransmissionCanceled()
                    navController.navigate(Screens.Home.route)
                },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.padding(bottom = 140.dp)
                ) {
                Text(
                    "Cancel & Return",
                    color = Color.DarkGray
                )
            }
        }
    }
}