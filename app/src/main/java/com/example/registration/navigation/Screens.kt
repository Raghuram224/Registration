package com.example.registration.navigation

import kotlinx.serialization.Serializable


const val IS_ADMIN_KEY = "isAdmin"
const val USER_ID_KEY = "userId"
const val TEST_KEY = "test"

sealed class Screens(val route: String) {
    data object LoginScreens : Screens(route = "LoginScreen")
    data object SignupScreens : Screens(route = "SignupScreen?$USER_ID_KEY={$USER_ID_KEY}") {
        fun passArgumentsSignup(userId: String? = null): String {
            return "SignupScreen?$USER_ID_KEY=$userId"
        }
    }

    //Required args
        data object ContactScreens : Screens(route = "ContactScreen/{$USER_ID_KEY}/{$IS_ADMIN_KEY}"){
        fun passArguments(userId:String?,isAdmin:Boolean):String{
            return "ContactScreen/$userId/$isAdmin"
        }
    }

    data object ViewProfileScreen:Screens(route = "ViewProfileScreen/{$USER_ID_KEY}"){
        fun passArgument(userId:String):String{
            return "ViewProfileScreen/$userId"
        }
    }

    data object AllContactsScreen : Screens(route = "AllContactsScreen")

//optional args
//    data object ContactScreens : Screens(route = "ContactScreen?userId={$USER_ID_KEY}&isAdmin={$IS_ADMIN_KEY}") {
//        fun passArguments(userId: String? = null, isAdmin: Boolean = false): String {
//            return "ContactScreen?$USER_ID_KEY=$userId&$IS_ADMIN_KEY=$isAdmin"
//        }
//    }


//    data object EditContactScreens : Screens(route = "EditContactScreen/{$USER_ID_KEY}"){
//        fun passArgumentUserID(userId: Int):String{
//            return  "EditContactScreen/$userId"
//        }
//    }

//    data object TestScreen : Screens(route = "TestScreen?${TEST_KEY}={${TEST_KEY}}") {
//        fun passArguments(userId: String): String {
//            return "TestScreen?test=$userId"
//        }
//    }
}


@Serializable
sealed class FeaturesScreens{
    @Serializable
    data object AllToDos:FeaturesScreens()
}