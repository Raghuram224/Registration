package com.example.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.registration.ui.theme.RegistrationTheme
import com.example.registration.view.loginScreen.LoginScreen
import com.example.registration.view.signupScreen.SignupScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistrationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                val navGraph = remember(navController) {

                    navController.createGraph(startDestination = "SignupScreen") {
                        composable(route = "LoginScreen") {
                            LoginScreen()
                        }
                        composable(route = "SignupScreen"){
                            SignupScreen()
                        }
                    }

                }

                NavHost(navController = navController, graph = navGraph)
            }
        }
    }
}

