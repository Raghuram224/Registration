package com.example.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.example.registration.constants.AllScreens
import com.example.registration.constants.constantModals.Screens
import com.example.registration.ui.theme.RegistrationTheme
import com.example.registration.view.contactScreen.ContactScreen
import com.example.registration.view.contactScreen.editContacts.EditContactScreen
import com.example.registration.view.loginScreen.LoginScreen
import com.example.registration.view.signupScreen.SignupScreen
import com.example.registration.viewModels.LoginViewModel
import com.example.registration.viewModels.ContactViewModel
import com.example.registration.viewModels.EditContactsViewmodel
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

                    navController.createGraph(startDestination = AllScreens.screen.loginScreen) {
                        composable(
                            route = AllScreens.screen.loginScreen,

                            ) {
                            val loginViewModel = hiltViewModel<LoginViewModel>()

                            LoginScreen(
                                navController = navController,
                                loginViewModel = loginViewModel,
                            )
                        }
                        composable(
                            route = AllScreens.screen.signupScreen,
                            enterTransition = {
                                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)

                            }, exitTransition = {
                                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                            }
                        ) {

                            val signupViewModel = hiltViewModel<SignupViewModel>()
                            SignupScreen(
                                signupViewModel = signupViewModel,
                                navController = navController,
                            )
                        }
                        composable(route = AllScreens.screen.contactProfileScreen){
                            val contactViewModel = hiltViewModel<ContactViewModel>()
                            ContactScreen(
                                navController = navController,
                                contactViewModel = contactViewModel,

                                )
                        }
                        composable(route = AllScreens.screen.editContactScreen) {
                            val editContactViewModel = hiltViewModel<EditContactsViewmodel>()

                            EditContactScreen(
                                editContactsViewModel = editContactViewModel,
                                navController = navController,

                                )
                        }


                    }

                }

                NavHost(navController = navController, graph = navGraph)
            }
        }


    }

}

