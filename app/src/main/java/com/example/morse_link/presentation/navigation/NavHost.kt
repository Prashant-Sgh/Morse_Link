package com.example.morse_link.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morse_link.presentation.components.ProcessingOverlay
import com.example.morse_link.presentation.components.ResultScreen
import com.example.morse_link.presentation.components.TransmitScreen
import com.example.morse_link.presentation.screens.HomeScreen
import com.example.morse_link.presentation.viewmodels.SharedViewmodel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    viewmodel: SharedViewmodel,
    modifier: Modifier
) {

    val message = viewmodel.messageString
    val isProcessing by viewmodel.isProcessing.collectAsState()
    val morseCode by viewmodel.morseCode.collectAsState()

    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            HomeScreen(navController = navController, viewmodel = viewmodel)
        }
        composable(Screens.Loading.route, ) {
            ProcessingOverlay(
                onComplete = {
                    viewmodel.UpdateProcessingState(false)
                    navController.navigate(Screens.Result.route)
                             },
                onCancel = {
                    viewmodel.UpdateProcessingState(false)
                    navController.navigate(Screens.Home.route)
                })
        }
        composable(Screens.Result.route) {
            ResultScreen(
                morseCode = morseCode,
                navcontroller = navController,
                transmit = {
//                    viewmodel.toggleTonePlayStatus()
                    viewmodel.TransmitSound(
                        morseCode = morseCode,
                        isPaused = viewmodel.isPause
                    )
                }
            )
        }
        composable(Screens.Transmit.route) {
            TransmitScreen(
                navController,
                { viewmodel.toggleTonePlayStatus() }
                )
        }
    }

}