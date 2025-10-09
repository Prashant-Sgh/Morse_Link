package com.example.morse_link.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow

//@Preview(showBackground = true)
@Composable
fun ResultScreen(morseCode: String) {

//    val morseCode = "... --- -- . / -- . ... ... .- --. . / --. --- / .... . .-. . --..-- / .. - / -.-. .- -. / -... . / .-.. --- -. --. --..-- / ...- . .-. -.-- / .-.. --- -. --. / --- .-. / ... .... --- .-. - --..-- / .. / -.. --- -. .----. - / -.- -. --- .-- / .. - .----. ... / .- -.-. - ..- .- .-.. / .-.. . -. --. - .... --..-- / -... ..- - / .. / -.-. .- -. / - .-. -.-- / - --- / -.-. --- ...- . .-. / .. - / - --- --- / -... -.-- / -.-. --- ...- . .-. .. -. --. / - .... .- - / .-- .... .. .-.. . / -.. . ... .. --. -. .. -. --. .-.-.- //\n"

    val scrollState =  rememberScrollState()
    val clipBoardManager = LocalClipboardManager.current

    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Sound", "Flashlight")
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
                    .padding(start = 46.dp, end = 46.dp, top = 60.dp, bottom = 40.dp)
            )

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    "copy",
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable(enabled = true, onClick = {
                        clipBoardManager.setText(AnnotatedString(morseCode))
                    })
                )

                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = morseCode,
                        softWrap = true,
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp
                    )
                }
            }

            Spacer(Modifier.height(70.dp))

            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color.DarkGray)
                ) {
                    Text("Do another one")
                }

                Spacer(Modifier.height(20.dp))



                Box {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Text("Transmit")
                    }
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = { expanded = false},
                        modifier = Modifier
                            .background(
                                Color.DarkGray,
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        options.forEach { label ->
                            DropdownMenuItem(
                                text = { Text(
                                    label,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) },
                                onClick = {
                                    /*TODO*/
                                    //                                repository.label
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}