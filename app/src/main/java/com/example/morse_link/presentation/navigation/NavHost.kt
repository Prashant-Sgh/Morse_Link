package com.example.morse_link.presentation.navigation

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morse_link.presentation.components.ProcessingOverlay
import com.example.morse_link.presentation.components.ResultScreen
import com.example.morse_link.presentation.components.TransmitScreen
import com.example.morse_link.presentation.screens.HomeScreen
import com.example.morse_link.presentation.viewmodels.SharedViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    viewmodel: SharedViewmodel,
    modifier: Modifier,
    requestCameraPermission: ActivityResultLauncher<String>
) {

    val morseCode by viewmodel.morseCode.collectAsState()
    val isLightTransmissionEnabled by viewmodel.isLightEnabled.collectAsState()
    val context = LocalContext.current

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
            requestCameraPermission.launch(Manifest.permission.CAMERA)
            ResultScreen(
                morseCode = morseCode,
                navcontroller = navController,
                transmitSound = {
                    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
                    viewmodel.TransmitSound(morseCode = morseCode, scope = scope)
                },
                transmitLight = {
                    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
                    viewmodel.TransmitFlashlight(
                        morseCode =  morseCode,
                        context = context,
                        scope = scope,
                        result = { viewmodel.toggleLightEnabled(!it) }
                    )
                },
                lightTransmission = isLightTransmissionEnabled
            )
        }
        composable(Screens.Transmit.route) {
            TransmitScreen(
                navController,
                onStateChange = { viewmodel.toggleTonePlayStatus()},
                onTransmissionCanceled = {
                    viewmodel.CancelTransmission(true)
                    viewmodel.UpdatePauseStatus(false)
                }
                )
        }
    }

}