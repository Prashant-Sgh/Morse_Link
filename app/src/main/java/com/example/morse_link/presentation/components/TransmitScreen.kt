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

//@Preview (showBackground = true)
@Composable
fun TransmitScreen(navController: NavHostController) {

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
                    .padding(start = 46.dp, end = 46.dp, top = 60.dp, bottom = 40.dp)
            )

            Spacer(Modifier.weight(0.35f))

//            Box(
//                modifier = Modifier
//                    .background(color = Color.Gray,
//                        shape = RoundedCornerShape(200.dp)
//                    )
//                    .size(280.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Box(
//                    modifier = Modifier
//                        .background(color = Color.DarkGray, shape = RoundedCornerShape(100.dp))
//                        .size(180.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("STOP", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 1.sp)
//                }
//            }

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
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isTransmitting) "STOP" else "Resume",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }

            Spacer(Modifier.weight(0.5f))

            OutlinedButton(
                onClick = {
                    navController.navigate(Screens.Home.route)
                },
                shape = RoundedCornerShape(4.dp),
//                colors = ButtonDefaults.buttonColors(Color.DarkGray),
                modifier = Modifier.padding(bottom = 140.dp)
                ) {
                Text(
                    "Go Home",
                    color = Color.DarkGray
                )
            }
        }
    }
}