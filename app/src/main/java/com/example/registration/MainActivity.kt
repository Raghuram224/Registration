package com.example.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.registration.navigation.AppNavGraph
import com.example.registration.ui.theme.RegistrationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegistrationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                AppNavGraph(navController = navController)
            }
        }


    }

}

