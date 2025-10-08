package com.example.morse_link.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morse_link.presentation.components.ProcessingOverlay
import com.example.morse_link.presentation.components.ResultScreen
import com.example.morse_link.presentation.screens.HomeScreen
import com.example.morse_link.presentation.viewmodels.SharedViewmodel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    viewmodel: SharedViewmodel
) {

    val message = viewmodel.messageString
    val morseCode = viewmodel.morseCode
    val isProcessing by viewmodel.isProcessing.collectAsState()

    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screens.Loading.route, ) {
            ProcessingOverlay(isProcessing = isProcessing)
        }

    }

}