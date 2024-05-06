package com.example.registration.navigation


const val IS_ADMIN_KEY="isAdmin"
const val USER_ID_KEY="userId"
const val NAVIGATED_FROM = "from"

sealed class Screens(val route: String) {
    data object LoginScreens : Screens(route = "LoginScreen")
    data object SignupScreens : Screens(route = "SignupScreen/{$USER_ID_KEY}/{$NAVIGATED_FROM}"){
        fun passArgumentsSignup(userId: Int,from:String):String{
            return "SignupScreen/$userId/$from"
        }
    }
    data object ContactScreens : Screens(route = "ContactScreen/{$USER_ID_KEY}/{$IS_ADMIN_KEY}"){
        fun passArguments(userId:Int,isAdmin:Boolean):String{
            return "ContactScreen/$userId/$isAdmin"
        }
    }
    data object EditContactScreens : Screens(route = "EditContactScreen/{$USER_ID_KEY}"){
        fun passArgumentUserID(userId: Int):String{
            return  "EditContactScreen/$userId"
        }
    }
    data object AllContactsScreen : Screens(route = "AllContactsScreen")
}
