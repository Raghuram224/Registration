package com.example.registration.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.registration.view.contactScreen.ContactScreen
import com.example.registration.view.contactScreen.ViewProfileScreen
import com.example.registration.view.contactScreen.allContacts.AllContactsScreen
import com.example.registration.view.loginScreen.LoginScreen
import com.example.registration.view.signupScreen.SignupScreen
import com.example.registration.viewModels.AllContactsViewModel
import com.example.registration.viewModels.ContactViewModel
import com.example.registration.viewModels.LoginViewModel
import com.example.registration.viewModels.SignupViewModel
import com.example.registration.viewModels.ViewProfileScreenViewModel

@Composable
fun AppNavGraph() {


    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.LoginScreens.route) {
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
            arguments = listOf(
                navArgument(USER_ID_KEY) {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                },

                ),
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)

            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {

            val signupViewModel = hiltViewModel<SignupViewModel>()
            SignupScreen(
                signupViewModel = signupViewModel,
                navController = navController,
            )
        }
        composable(
            route = Screens.ContactScreens.route,
            arguments = listOf(
                navArgument(USER_ID_KEY) {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true

                },
                navArgument(IS_ADMIN_KEY) {
                    type = NavType.BoolType
                    defaultValue = false

                }
            ),
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)

            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            val contactViewModel = hiltViewModel<ContactViewModel>()
            ContactScreen(
                navController = navController,
                contactViewModel = contactViewModel,

                )
        }

        composable(
            route = Screens.ViewProfileScreen.route,
            arguments = listOf(
                navArgument(USER_ID_KEY) {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true

                },
            ),
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)

            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            val viewProfileScreenViewModel = hiltViewModel<ViewProfileScreenViewModel>()
            ViewProfileScreen(
                navController = navController,
                viewProfileScreenViewModel = viewProfileScreenViewModel
            )
        }
        composable(
            route = Screens.AllContactsScreen.route,

            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)

            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            val allContactsViewModel = hiltViewModel<AllContactsViewModel>()

            AllContactsScreen(
                modifier = Modifier,
                allContactsViewModel = allContactsViewModel,
                navController = navController
            )
        }

        composable<FeaturesScreens.AllToDos>{

        }

//        composable(
//            route = Screens.TestScreen.route,
//            arguments = listOf(
//                navArgument(TEST_KEY) {
//                    type = NavType.StringType
//                    nullable = true
//                    defaultValue = null
//                },
//
//                ),
//
//        ) {
//
//            TestScreen(
//                navController = navController,
//            )
//        }
    }
}