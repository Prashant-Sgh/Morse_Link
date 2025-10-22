package com.example.morse_link.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DropdownButton(
    transmitSound: () -> Unit, transmitLight: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Transmit") }
    val options = listOf("Both", "Light", "Sound")

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column {
            Button(
                onClick = { expanded = true },
                colors = ButtonDefaults.buttonColors(Color.Black),
                shape = RoundedCornerShape(4.dp)
                ) {
                Text(selectedOption)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                Modifier.background(Color.DarkGray)
            ) {
                options.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(
                            label,
                            color = Color.White
                        ) },
                        onClick = {
                            selectedOption = label
                            expanded = false
                            when(label) {
                                "Sound" -> transmitSound()
                                "Light" -> transmitLight()
                                "Both" -> {
                                    transmitSound()
                                    transmitLight()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
