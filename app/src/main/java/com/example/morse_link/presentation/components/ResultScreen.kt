package com.example.morse_link.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.morse_link.presentation.navigation.Screens

@Composable
fun ResultScreen(morseCode: String, navcontroller: NavHostController, transmitSound : () -> Unit, transmitLight : () -> Unit, lightTransmission: Boolean) {

    val scrollState =  rememberScrollState()
    val clipBoardManager = LocalClipboardManager.current

    val options = listOf("Sound", "Flashlight")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 46.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                "Morse code is ready",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp, top = 100.dp, bottom = 80.dp)
            )

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    "copy",
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable(
                        enabled = true,
                        onClick = {
                        clipBoardManager.setText(AnnotatedString(morseCode))
                    })
                )

                Box(
                    modifier = Modifier
                        .height(500.dp)
                        .padding(horizontal = 10.dp)
                        .verticalScroll(scrollState)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .heightIn(max = 300.dp)
                                .verticalScroll(scrollState),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = morseCode,
                                softWrap = true,
                                letterSpacing = 1.sp,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Light,
                                maxLines = 18,
                                textAlign = TextAlign.Center,
                            )
                        }

                        Spacer(Modifier.height(20.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DropdownButton(transmitSound, transmitLight)

                            Button(
                                onClick = {
                                    navcontroller.navigate(Screens.Home.route)
                                },
                                colors = ButtonDefaults.buttonColors(Color.Transparent)
                            ) {
                                Text(
                                    "Do another one",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }

                        }
                    }


                }
            }

            Spacer(Modifier.weight(1f))

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 95.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
            }
        }
    }
}