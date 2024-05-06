package com.example.registration.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.registration.view.contactScreen.ContactScreen
import com.example.registration.view.contactScreen.allContacts.AllContactsScreen
import com.example.registration.view.contactScreen.editContacts.EditContactScreen
import com.example.registration.view.loginScreen.LoginScreen
import com.example.registration.view.signupScreen.SignupScreen
import com.example.registration.viewModels.AllContactsViewModel
import com.example.registration.viewModels.ContactViewModel
import com.example.registration.viewModels.EditContactsViewmodel
import com.example.registration.viewModels.LoginViewModel
import com.example.registration.viewModels.SignupViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
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
        composable(
            route = Screens.ContactScreens.route,
            arguments = listOf(
                navArgument(USER_ID_KEY) {
                    type = NavType.IntType
                },
                navArgument(IS_ADMIN_KEY) {
                    type = NavType.BoolType
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
            route = Screens.EditContactScreens.route,
            arguments = listOf(
                navArgument(USER_ID_KEY) {
                    type = NavType.IntType
                },

            ),
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)

            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            val editContactViewModel = hiltViewModel<EditContactsViewmodel>()

            EditContactScreen(
                editContactsViewModel = editContactViewModel,
                navController = navController,

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
    }
}