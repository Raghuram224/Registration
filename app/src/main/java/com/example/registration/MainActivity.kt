package com.example.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.registration.constants.Screens
import com.example.registration.ui.theme.RegistrationTheme
import com.example.registration.view.contactScreen.allContacts.AllContactsScreen
import com.example.registration.view.contactScreen.ContactScreen
import com.example.registration.view.contactScreen.editContacts.EditContactScreen
import com.example.registration.view.loginScreen.LoginScreen
import com.example.registration.view.signupScreen.SignupScreen
import com.example.registration.viewModels.AllContactsViewModel
import com.example.registration.viewModels.ContactViewModel
import com.example.registration.viewModels.EditContactsViewmodel
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

                    navController.createGraph(startDestination = Screens.LoginScreens.route) {
                        composable(
                            route = Screens.LoginScreens.route,

                            ) {
                            val loginViewModel = hiltViewModel<LoginViewModel>()

                            LoginScreen(
                                navController = navController,
                                loginViewModel = loginViewModel,
                            )
                        }
                        composable(
                            route = Screens.SignupScreens.route,
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
                        composable(route = Screens.ContactScreens.route) {
                            val contactViewModel = hiltViewModel<ContactViewModel>()
                            ContactScreen(
                                navController = navController,
                                contactViewModel = contactViewModel,

                                )
                        }
                        composable(route = Screens.EditContactScreens.route) {
                            val editContactViewModel = hiltViewModel<EditContactsViewmodel>()

                            EditContactScreen(
                                editContactsViewModel = editContactViewModel,
                                navController = navController,

                                )
                        }
                        composable(route = Screens.AllContactsScreen.route) {
                            val allContactsViewModel = hiltViewModel<AllContactsViewModel>()

                            AllContactsScreen(
                                modifier = Modifier,
                                allContactsViewModel = allContactsViewModel,
                                navController = navController
                            )
                        }


                    }

                }

                NavHost(navController = navController, graph = navGraph)
            }
        }


    }

}

