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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.morse_link.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    var isProcessing by remember { mutableStateOf(false) }

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
                textAlign = TextAlign.Center,
                fontSize = 23.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 46.dp, end = 46.dp, top = 60.dp, bottom = 40.dp)
            )
            Text(
                "Enter your message here and press convert.",
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 46.dp)
                )

            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 48.dp)
                    .background(color = Color.Transparent),
                value = "Some message go here, it can be long, very long or short, I don't know it's actual length, but i can try to cover it too by covering that while designing. Some message go here, it can be long, very long or short, I don't know it's actual length, but i can try to cover it too by covering that while designing. Some message go here, it can be long, very long or short, I don't know it's actual length, but i can try to cover it too by covering that while designing. Some message go here, it can be long, very long or short, I don't know it's actual length, but i can try to cover it too by covering that while designing. Some message go here, it can be long, very long or short, I don't know it's actual length, but i can try. Some message go here, it can be long, very long or short, I don't know it's actual length, but i can try. Some message go here, it can be long, very long or short, I don't know it's actual length, but i can try.Some message go here, it can be long, very long or short, I don't know it's actual length, but i can try. Some message go here, it can be long, very long or short, I don't know it's actual length, but i can try. ",
//                value = "",
                onValueChange = {},
                label = { Text("Message", fontWeight = FontWeight.Light) },
                textStyle = TextStyle(color = Color(0xFF595959), fontSize = 16.sp),
                enabled = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF19C001),
                    focusedLabelColor = Color(0xFF19C001),
                    unfocusedBorderColor = Color(0xFF3054FF),
                    unfocusedLabelColor = Color(0xFF3054FF),
//                    unfocusedBorderColor = Color(0xFF19C001),
//                    unfocusedLabelColor = Color(0xFF19C001),
                    cursorColor = Color(0xFF19C001),
                    unfocusedTrailingIconColor = Color.Gray,
                    focusedTrailingIconColor = Color.DarkGray
                ),
                singleLine = false,
                maxLines = 22,
            )

            Row (
                Modifier.padding(horizontal = 52.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "clear",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Text(
                    "paste",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    isProcessing = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                Text("Convert")
            }
        }

        ProcessingOverlay(isProcessing)

        Column {
            Spacer(modifier = Modifier.weight(0.8f))
            Text(
                "Cancel",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.clickable(enabled = true, onClick = {
                    isProcessing = false
                })
            )
            Spacer(modifier = Modifier.weight(0.2f))
        }
    }
}