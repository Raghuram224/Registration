package com.example.registration

import android.annotation.SuppressLint
import java.util.Calendar;
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date

//fun yearsToMillis(years: Long): Long {
//    val days = years * 365
//    val hours = days * 24
//    val minutes = hours * 60
//    val seconds = minutes * 60
//    val milliseconds = seconds * 1000
//    return milliseconds
//}
//
//@SuppressLint("SimpleDateFormat")
//fun convertMillisToDate(mill: Long?): String {
//    val format = SimpleDateFormat("dd/MM/yyyy")
//
//    return if (mill != null) format.format(Date(mill)) else ""
//}
//fun main() {
//    println( yearsToMillis(4))
//    println(convertMillisToDate(Date().time.minus(126144000000)))
//}