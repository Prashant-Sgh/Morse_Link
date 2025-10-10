package com.example.morse_link.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.morse_link.presentation.navigation.AppNavHost
import com.example.morse_link.presentation.theme.Morse_LinkTheme
import com.example.morse_link.presentation.viewmodels.SharedViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: SharedViewmodel by viewModels()
            val navController: NavHostController = rememberNavController()
            Morse_LinkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    MainScreen(modifier = Modifier.padding(innerPadding))
                    AppNavHost(
                        navController = navController,
                        viewmodel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                        )
                }
            }
        }
    }
}