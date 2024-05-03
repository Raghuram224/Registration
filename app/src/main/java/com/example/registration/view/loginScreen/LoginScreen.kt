package com.example.registration.view.loginScreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.example.registration.constants.AllScreens
import com.example.registration.constants.constantModals.Screens
import com.example.registration.viewModels.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
        .background(Color.White),
    navController: NavController,
    loginViewModel: LoginViewModel,

) {
//    val configuration = LocalConfiguration.current

    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.passwords.collectAsState()

    LoginPortrait(
        signupNavigation = {

            navController.navigate(AllScreens.screen.signupScreen)
        },
        loginViewModel = loginViewModel,
        successNavigation = {
            navController.popBackStack()
            navController.navigate(AllScreens.screen.contactProfileScreen)
        },
        email = email,
        password = password,

        )

//    when (configuration.orientation) {
//        Configuration.ORIENTATION_PORTRAIT ->
//            LoginPortrait(
//                signupNavigation = {
//
//                    navController.navigate(screens.loginScreen)
//                },
//                loginViewModel = loginViewModel,
//                successNavigation = {
//                    navController.popBackStack()
//                    navController.navigate(screens.contactProfileScreen)
//                },
//                email = email,
//                password = password,
//
//            )
//
//        Configuration.ORIENTATION_LANDSCAPE -> {
//            LoginLandScape(
//                email = email,
//                password = password,
//
//                signupNavigation = {
//                    navController.navigate("SignupScreen")
//                },
//                successNavigation = {
//                    navController.popBackStack()
//                    navController.navigate("DataScreen")
//                },
//                loginViewModel = loginViewModel
//            )
//        }
//    }


}


