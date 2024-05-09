package com.example.registration

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.registration.navigation.TEST_KEY

//@Composable
//fun TestScreen(
//    navController: NavController
//) {
//    Text(text = "Test screen")
//    Log.i("test backstack id",navController.currentBackStackEntry?.arguments?.getString(TEST_KEY).toString())
//
//
//}


//    const val EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
//const val EMAIL_VALIDATION_REGEX ="^[a-zA-Z0-9.]+@[a-zA-Z.]{2,}\\.[a-z]{2,}$"
////                                    "^[a-zA-Z0-9.]+@[a-zA-Z.]{1,20}\\.[a-zA-Z]{2,}$"
//
//fun main() {
//    val mail = "aasdf@qqAA.co"
//
//    if (mail.matches(regex = Regex(EMAIL_VALIDATION_REGEX))){
//        println("T")
//    }else{
//        println("F")
//    }
//}

fun main() {
    val a = arrayListOf<String>("")

    println(a.ifEmpty { null })
}