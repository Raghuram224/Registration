package com.example.registration.constants.constantModals

import android.graphics.Bitmap

data class Screens(
    val loginScreen:String = "LoginScreen",
    val signupScreen:String = "SignupScreen",
    val contactProfileScreen:String = "ContactProfileScreen",
    val editContactScreen :String = "EditContactScreen"
)

data class PersonalInformation(
    var firstName: String,
    var lastName: String,
    var age: String,
    var address: String,
    var dob: String,
    var primaryEmail: String,
    var primaryPhone: String,
    var otherEmails: List<String>?,
    var otherPhones: List<String>?,
    var profileImage: Bitmap?,
    var website: String,

    )

enum class LoginInputFields {
    Email,
    Password,
}
enum class TextFieldType {
    FirstName,
    LastName,
    Age,
    Address,
    DOB,
    PrimaryEmail,
    PrimaryPhone,
    Password,
    Website


}

enum class OtherEmailOrPhoneFields {
    OtherEmail,
    OtherPhones,
}
data class UserDetails(
    var firstName: String,
    var lastName: String,
    var age: String,
    var address: String,
    var dob: String,
    var primaryEmail: String,
    var primaryPhone: String,
    var otherEmails: String?,
    var otherPhones: String?,
    var website: String,
    var password: String,
    var profileImage:Bitmap?

)


enum class Keyboard {
    Opened, Closed
}

