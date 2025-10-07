package com.example.morse_link.presentation.navigation

sealed class Screens(val route: String) {
    object Home: Screens("Home")
    object Loading: Screens("Loading")
    object Result: Screens("Result")
    object Transmit: Screens("Transmit")
}