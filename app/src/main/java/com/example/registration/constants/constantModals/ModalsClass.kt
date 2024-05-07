package com.example.registration.constants.constantModals

import android.graphics.Bitmap


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
    var profileImage: Bitmap?

)


enum class KeyboardStatus {
    Opened, Closed
}

enum class CredentialsValidationStatus {
    EmailError,
    PasswordError,
    ValidCredentials,

}

enum class UserType {
    Admin,
    Client
}
enum class SignupFieldsColorType{
    FName,
    LName,
    Password,
    ConfirmPassword
}
enum class EditFieldsColorType{
    FName,
    LName,
}
data class ContactBasicDetails(
    val fName: String,
    val lName: String,
    val userId: Int,
    val profileImage: Bitmap?
)

data class FieldsColor(
    var fNameColor: Boolean,
    var lNameColor: Boolean,
    var passwordColor: Boolean,
    var confirmPasswordColor: Boolean,

    )
