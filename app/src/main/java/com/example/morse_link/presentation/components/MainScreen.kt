package com.example.morse_link.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.morse_link.R
import com.example.morse_link.presentation.navigation.Screens
import com.example.morse_link.presentation.viewmodels.SharedViewmodel

//@Preview(showBackground = true)
@Composable
fun MainScreen(
    viewmodel: SharedViewmodel,
    navController: NavController
    ) {

    var message by remember { mutableStateOf("") }
    val clipBoardManager = LocalClipboardManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "English - Morse.",
                fontSize = 23.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 46.dp, end = 46.dp, top = 60.dp, bottom = 40.dp)
            )
            Text(
                "Enter your message here and press convert.",
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 46.dp)
                )

            Column(
                modifier = Modifier.width(300.dp)
            ){
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth()
                        .background(color = Color.Transparent),

                    value = message,
                    onValueChange = { message = it},
                    label = { Text("Message", fontWeight = FontWeight.Normal) },
                    textStyle = TextStyle(
                        color = Color(0xFF595959),
                        fontSize = 15.sp,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center
                        ),
                    enabled = true,
                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = Color(0xFF19C001),
//                        focusedLabelColor = Color.Black,
                        focusedBorderColor = Color(0xFF3054FF),
                        focusedLabelColor = Color(0xFF3054FF),
                        unfocusedBorderColor = Color(0xFF3054FF),
                        unfocusedLabelColor = Color(0xFF3054FF),
                        cursorColor = Color(0xFF3054FF),
                    ),
                    singleLine = false,
                    maxLines = 22,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, start = 6.dp, end = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "clear",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .clickable(
                                enabled = true,
                                onClick = {
                                    message = ""
                                    viewmodel.UpdateMessage(message)
                                }
                            )
                    )
                    Text(
                        "paste",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.clickable(enabled = true, onClick = {
                            clipBoardManager.getText()?.let {
                                message = it.text
                            }
                        })
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    viewmodel.UpdateMessage(message)
                    viewmodel.UpdateProcessingState(true)
                    viewmodel.ConvertToMorse(message)
                    navController.navigate(Screens.Loading.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 46.dp,
                        end = 46.dp,
                        bottom = 50.dp
                    ),
                colors = ButtonDefaults.buttonColors(Color(0xFF3054FF))
                ) {
                Text("Convert")
            }
        }
    }
}