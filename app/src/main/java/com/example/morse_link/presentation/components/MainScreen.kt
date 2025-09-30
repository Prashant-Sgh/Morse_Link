package com.example.morse_link.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
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
                    .padding(horizontal = 46.dp, 60.dp)
            )
            Text(
                "Enter your message here and press convert.",
                textAlign = TextAlign.Center,
                fontSize = 21.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 46.dp)
                )

            OutlinedTextField(
                modifier = Modifier.padding(top = 20.dp)
                    .background(color = Color.Transparent),
                value = "",
                onValueChange = {},
                label = { Text("Message", fontWeight = FontWeight.Light) },
                enabled = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF17B200),
                    focusedLabelColor = Color(0xFF3054FF),
                    unfocusedBorderColor = Color(0xFF3054FF),
                    unfocusedLabelColor = Color(0xFF3054FF)
                )
            )
        }
    }
}