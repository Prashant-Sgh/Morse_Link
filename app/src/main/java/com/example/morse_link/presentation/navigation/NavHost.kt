package com.example.morse_link.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morse_link.presentation.components.ProcessingOverlay
import com.example.morse_link.presentation.components.ResultScreen
import com.example.morse_link.presentation.screens.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screens.Loading.route, ) {
            ProcessingOverlay()
        }

    }

}