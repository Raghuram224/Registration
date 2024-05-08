package com.example.registration.navigation


const val IS_ADMIN_KEY = "isAdmin"
const val USER_ID_KEY = "userId"
const val NAVIGATED_FROM = "from"
const val TEST_KEY = "test"

sealed class Screens(val route: String) {
    data object LoginScreens : Screens(route = "LoginScreen")
    data object SignupScreens : Screens(route = "SignupScreen?$USER_ID_KEY={$USER_ID_KEY}") {
        fun passArgumentsSignup(userId: String? = null): String {
            return "SignupScreen?$USER_ID_KEY=$userId"
        }
    }

    //optional args
    data object ContactScreens :
        Screens(route = "ContactScreen?userId={$USER_ID_KEY}&&isAdmin={$IS_ADMIN_KEY}") {
        fun passArguments(userId: String? = null, isAdmin: Boolean = false): String {
            return "ContactScreen?$USER_ID_KEY=$userId&&$IS_ADMIN_KEY=$isAdmin"
        }
    }

    //Required args
//    data object ContactScreens : Screens(route = "ContactScreen/{$USER_ID_KEY}/{$IS_ADMIN_KEY}"){
//        fun passArguments(userId:Int,isAdmin:Boolean):String{
//            return "ContactScreen/$userId/$isAdmin"
//        }
//    }
//    data object EditContactScreens : Screens(route = "EditContactScreen/{$USER_ID_KEY}"){
//        fun passArgumentUserID(userId: Int):String{
//            return  "EditContactScreen/$userId"
//        }
//    }
    data object AllContactsScreen : Screens(route = "AllContactsScreen")
    data object TestScreen : Screens(route = "TestScreen?${TEST_KEY}={${TEST_KEY}}") {
        fun passArguments(userId: String): String {
            return "TestScreen?test=$userId"
        }
    }
}
