package com.example.registration

fun main(){
    val reg = "[a-zA-Z]+\\d\\S"

    val input = "raghuram2gamil.com"

//    val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
    val regex = Regex("(?=.*[A-Za-z])(?=.*)\\S+@\\S+\\.\\S+")



    if (regex.matches(input)){
        println("true")
    }else{
        println("False")
    }
}