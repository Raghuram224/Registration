package com.example.registration.view.loginScreen

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.registration.constants.InputsRegex
import com.example.registration.viewModels.LoginInputFields
import com.example.registration.viewModels.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
        .background(Color.White),
    navController: NavController,
    loginViewModel: LoginViewModel,

) {
    val configuration = LocalConfiguration.current

    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.passwords.collectAsState()


    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT ->
            LoginPortrait(
                signupNavigation = {
                    navController.navigate("SignupScreen")
                },
                loginViewModel = loginViewModel,
                successNavigation = {
                    navController.popBackStack()
                    navController.navigate("ProfileScreen")
                },
                email = email,
                password = password,

            )

        Configuration.ORIENTATION_LANDSCAPE -> {
            LoginLandScape(
                email = email,
                password = password,

                signupNavigation = {
                    navController.navigate("SignupScreen")
                },
                successNavigation = {
                    navController.popBackStack()
                    navController.navigate("DataScreen")
                },
                loginViewModel = loginViewModel
            )
        }
    }


}


