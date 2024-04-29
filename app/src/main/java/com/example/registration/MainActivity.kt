package com.example.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.registration.ui.theme.RegistrationTheme
import com.example.registration.view.DataScreen
import com.example.registration.view.loginScreen.LoginScreen
import com.example.registration.view.signupScreen.SignupScreen
import com.example.registration.viewModels.LoginViewModel
import com.example.registration.viewModels.SignupViewModel
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

                    navController.createGraph(startDestination = "LoginScreen") {
                        composable(route = "LoginScreen") {
                            val loginViewModel = hiltViewModel<LoginViewModel>()


                            LoginScreen(
                                navController = navController,
                                loginViewModel = loginViewModel,

                                )
                        }
                        composable(route = "SignupScreen") {

                            val signupViewModel = hiltViewModel<SignupViewModel>()
                            SignupScreen(
                                signupViewModel = signupViewModel,
                                navController = navController
                            )
                        }
                        composable(route = "DataScreen") {
                            DataScreen(
                                navController = navController
                            )
                        }


                    }

                }

                NavHost(navController = navController, graph = navGraph)
            }
        }


    }

//    private fun hasRequiredPermission(): Boolean {
//        return PERMISSIONS.all {
//            ContextCompat.checkSelfPermission(
//                applicationContext,
//                it
//            ) == PackageManager.PERMISSION_GRANTED
//        }
//    }
//
//    companion object {
//        private val PERMISSIONS = arrayOf(
//            Manifest.permission.CAMERA
//        )
//    }
}

