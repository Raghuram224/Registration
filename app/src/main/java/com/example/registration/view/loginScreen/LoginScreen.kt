package com.example.registration.view.loginScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.registration.navigation.Screens
import com.example.registration.constants.constantModals.UserType
import com.example.registration.viewModels.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
        .background(Color.White),
    navController: NavController,
    loginViewModel: LoginViewModel,

    ) {
//    val configuration = LocalConfiguration.current


    Log.i("login screen", "Login")
    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.passwords.collectAsState()

    LoginPortrait(
        signupNavigation = {
            navController.navigate(route = Screens.SignupScreens.passArgumentsSignup(
                userId = -1,
                from = "login"
            ))
        },
        loginViewModel = loginViewModel,
        successNavigation = { userType ->
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "userId",
                loginViewModel.userId
            )


            if (userType == UserType.Admin) {
                navController.navigate(Screens.AllContactsScreen.route) {
                    navController.popBackStack()
                }
            } else {

                navController.navigate(
                    Screens.ContactScreens.passArguments(
                        isAdmin = false,
                        userId = loginViewModel.userId
                    )
                ) {
                    navController.popBackStack()

                }
            }

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


