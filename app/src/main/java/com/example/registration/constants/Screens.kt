package com.example.registration.constants

import com.example.registration.constants.constantModals.Screens

object AllScreens {
    val screen = Screens()
}

sealed class Screen(val route:String){
    object LoginScreen:Screen(route = "LoginScreen")
    object SignupScreen:Screen(route = "SignupScreen")
    object ContactScreen:Screen(route = "ContactScreen")
    object EditContactScreen:Screen(route = "EditContactScreen")
}