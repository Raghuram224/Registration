package com.example.registration.constants


sealed class Screens(val route: String) {
    data object LoginScreens : Screens(route = "LoginScreen")
    data object SignupScreens : Screens(route = "SignupScreen")
    data object ContactScreens : Screens(route = "ContactScreen")
    data object EditContactScreens : Screens(route = "EditContactScreen")
    data object AllContactsScreen : Screens(route = "AllContactsScreen")
}
