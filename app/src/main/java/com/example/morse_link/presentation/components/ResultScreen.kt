package com.example.morse_link.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.morse_link.data.hardware.AccessCamera
import com.example.morse_link.presentation.navigation.Screens

//@Preview(showBackground = true)
@Composable
fun ResultScreen(morseCode: String, navcontroller: NavHostController, transmitSound : () -> Unit, transmitLight : () -> Unit, lightTransmission: Boolean) {

//    val morseCode = "... --- -- . / -- . ... ... .- --. . / --. --- / .... . .-. . --..-- / .. - / -.-. .- -. / -... . / .-.. --- -. --. --..-- / ...- . .-. -.-- / .-.. --- -. --. / --- .-. / ... .... --- .-. - --..-- / .. / -.. --- -. .----. - / -.- -. --- .-- / .. - .----. ... / .- -.-. - ..- .- .-.. / .-.. . -. --. - .... --..-- / -... ..- - / .. / -.-. .- -. / - .-. -.-- / - --- / -.-. --- ...- . .-. / .. - / - --- --- / -... -.-- / -.-. --- ...- . .-. .. -. --. / - .... .- - / .-- .... .. .-.. . / -.. . ... .. --. -. .. -. --. .-.-.- //\n"

    val scrollState =  rememberScrollState()
    val clipBoardManager = LocalClipboardManager.current

//    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Sound", "Flashlight")
    var isExpanded by remember { mutableStateOf(true) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

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
                fontSize = 23.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 46.dp, end = 46.dp, top = 100.dp, bottom = 80.dp)
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
                        .padding(horizontal = 15.dp)
                        .verticalScroll(scrollState)
                ) {
                    Column {
                        Text(
                            text = morseCode,
                            softWrap = true,
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp
                        )

                        Text("VVVVVVVVVVVV")

                        Button(
                            onClick = {
                                navcontroller.navigate(Screens.Home.route)
                            },
                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                        ) {
                            Text("Do another one", color = Color.DarkGray)
                        }

                        Spacer(Modifier.width(44.dp))

                        OutlinedButton(
                            onClick = {
                                navcontroller.navigate(Screens.Transmit.route)
                                transmitSound()
                            },
                        ) {
                            Text(
                                "Transmit sound",
                                fontWeight = FontWeight.Medium,
                                color = if (AccessCamera().hasCamera(LocalContext.current)) Color.Green else Color.Black
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                navcontroller.navigate(Screens.Transmit.route)
                                transmitLight()
                            },
                            enabled = lightTransmission
                        ) {
                            Text(
                                "Transmit Light",
                                fontWeight = FontWeight.Medium,
                                color = if (lightTransmission) Color.Green else Color.Black
                            )
                        }
//                      Transmit BOTH
                        OutlinedButton(
                            onClick = {
                                navcontroller.navigate(Screens.Transmit.route)
                                transmitLight()
                                transmitSound()
                            },
                            enabled = lightTransmission
                        ) {
                            Text(
                                "Transmit BOTH",
                                fontWeight = FontWeight.Medium,
                                color = if (lightTransmission) Color.Green else Color.Black
                            )
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
//                Button(
//                    onClick = {
//                        navcontroller.navigate(Screens.Home.route)
//                    },
//                    colors = ButtonDefaults.buttonColors(Color.Transparent)
//                ) {
//                    Text("Do another one", color = Color.DarkGray)
//                }
//
//                Spacer(Modifier.width(44.dp))
//                OutlinedButton(
//                    onClick = {
//                        //                            isExpanded = !isExpanded
//                        navcontroller.navigate(Screens.Transmit.route)
//                        transmitSound()
//                    },
//                    //                        colors = ButtonDefaults.buttonColors(Color.DarkGray)
//                ) {
//                    Text(
//                        "Transmit sound",
//                        fontWeight = FontWeight.Medium,
//                        color = Color.Black
//                    )
//                }
//
//                OutlinedButton(
//                    onClick = {
//                        //                            isExpanded = !isExpanded
//                        navcontroller.navigate(Screens.Transmit.route)
//                        transmitLight()
//                    },
//                    enabled = lightTransmission
//                    //                        colors = ButtonDefaults.buttonColors(Color.DarkGray)
//                ) {
//                    Text(
//                        "Transmit Light",
//                        fontWeight = FontWeight.Medium,
//                        color = Color.Black
//                    )
//                }

            }
        }
    }
}