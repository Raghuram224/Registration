package com.example.registration.constants

object InputsRegex {
    val EMAIL_REGEX ="[a-z0-9,@\\.]{0,100}"
    val NAME_REGEX ="^[a-zA-Z]{0,50}"
    val PHONE_NUMBER_REGEX ="[\\d,\\(,\\+,\\)]{0,14}"
    val AGE_REGEX = "^[0-9]{0,3}"
    val ALLOW_ANY_REGEX ="[\\w\\d\\s\\/\\-]{0,256}"

    val PASSWORD_REGEX = "[0-9a-zA-z,@!<>_#]{0,16}"
}