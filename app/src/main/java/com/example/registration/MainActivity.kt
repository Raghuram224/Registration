package com.example.registration

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import com.example.registration.viewModels.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            RegistrationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()


                val navGraph = remember(navController) {

                    navController.createGraph(startDestination = "LoginScreen") {
                        composable(route = "LoginScreen") {
                            LoginScreen(navController = navController)
                        }
                        composable(route = "SignupScreen") {

                            val signupViewModel = hiltViewModel<SignupViewModel>()
                            SignupScreen(
                                signupViewModel = signupViewModel,
                                navController = navController
                            )
                        }
                        composable(route = "DataScreen") {
                            DataScreen()
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

