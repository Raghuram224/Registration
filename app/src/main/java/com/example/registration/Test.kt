package com.example.registration
import android.util.Patterns.EMAIL_ADDRESS
fun main() {
    if (EMAIL_ADDRESS.matcher("raghu@gmail.com").matches()){
        println("Tr")
    }else{
        println("Fa")
    }
}