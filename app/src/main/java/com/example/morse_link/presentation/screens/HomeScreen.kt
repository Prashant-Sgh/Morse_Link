package com.example.morse_link.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.morse_link.presentation.components.MainScreen
import com.example.morse_link.presentation.viewmodels.SharedViewmodel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController, viewmodel: SharedViewmodel) {
    MainScreen(viewmodel = viewmodel, navController = navController)
}