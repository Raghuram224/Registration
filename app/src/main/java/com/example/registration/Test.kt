package com.example.registration

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.registration.navigation.TEST_KEY
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

//fun main() {
//    val yearsMill = yearsToMillis(20)
//    val res = convertMillisToDate(Date().time.minus(yearsMill))
//    println(res)
//}
//
//fun yearsToMillis(years: Long): Long {
//    val days = years * 365
//    val hours = days * 24
//    val minutes = hours * 60
//    val seconds = minutes * 60
//    return seconds * 1000
//}
//
//@SuppressLint("SimpleDateFormat")
//fun convertMillisToDate(mill: Long?): String {
//    val format = SimpleDateFormat("dd/MM/yyyy")
//
//    return if (mill != null) format.format(Date(mill)) else ""
//}
//
//fun milliToYears(milliseconds: Long): String {
//    val totalSeconds = milliseconds / 1000
//    val minutes = totalSeconds / 60
//    val hour = minutes / 60
//    val day = hour / 24
//    val year = (day / 365)
//
//    return year.toString()
//}
//
//fun yearToMillis(years: Long): Long {
//    val days = years * 365
//    val hours = days * 24
//    val minutes = hours * 60
//    val seconds = minutes * 60
//    return seconds * 1000
//}

@Composable
fun TestScreen(modifier: Modifier = Modifier) {
    
}