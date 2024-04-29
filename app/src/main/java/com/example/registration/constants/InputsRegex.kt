package com.example.registration.constants

object InputsRegex {

    const val EMAIL_ALLOWED_REGEX = "[a-z0-9,@\\.]{0,100}"
    const val EMAIL_VALIDATION_REGEX ="^[a-z0-9,\\.]{0,100}+@[a-z0-9]{0,20}+\\.[a-z]{0,10}"
    const val NAME_REGEX ="^[a-zA-Z]{0,50}"
    const val PHONE_NUMBER_REGEX ="[\\d,\\(,\\+,\\)]{0,14}"
    const val AGE_REGEX = "^[0-9]{0,3}"
    const val ALLOW_ANY_REGEX ="[\\w\\d\\s\\/\\-]{0,256}"

    const val PASSWORD_REGEX = "[0-9a-zA-z,@!<>_#]{0,16}"
}