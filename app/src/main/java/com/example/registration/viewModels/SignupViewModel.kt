package com.example.registration.viewModels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.constants.InputsRegex
import com.example.registration.modal.LocalDBRepo
import com.example.registration.permissionHandler.PermissionHandler
import com.example.registration.view.signupScreen.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


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


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val localDBRepo: LocalDBRepo,
    private val permissionHandler: PermissionHandler
) : ViewModel() {

    private val _signupData = MutableStateFlow(
        setContactsDetails()
    )

    private var _profileImage = MutableStateFlow<Bitmap?>(_signupData.value.profileImage)
    private val _isNavigatedFromContactScreen = MutableStateFlow(!localDBRepo.isLocalDbEmpty())

    val signupData = _signupData.asStateFlow()
    var emailList = mutableStateListOf("")
    var phoneList = mutableStateListOf("")
    val profileImage = _profileImage.asStateFlow()
    val emailListColor = mutableStateListOf(false)
    val phoneListColor = mutableStateListOf(false)
    val isNavigatedFromContactScreen = _isNavigatedFromContactScreen.asStateFlow()

    lateinit var userDetails: UserDetails


    fun convertListToString(list: List<String>, idx: Int): String? {
        var str = ""
        list.forEachIndexed { index, item ->
            if (index != idx && item.isNotEmpty()) {
                str += "$item,"
            }

        }
        return if (str.isNotEmpty()) str else null
    }


    fun updateSignupData(text: String, type: TextFieldType) {
        when (type) {
            TextFieldType.FirstName -> {
                _signupData.update {
                    it.copy(firstName = text)
                }
            }

            TextFieldType.LastName -> {
                _signupData.update {
                    it.copy(lastName = text)
                }
            }

            TextFieldType.Age -> {
                _signupData.update {
                    it.copy(age = text)
                }
            }

            TextFieldType.Address -> {
                _signupData.update {
                    it.copy(address = text)
                }
            }

            TextFieldType.DOB -> {
                _signupData.update {
                    it.copy(dob = text)
                }
            }

            TextFieldType.PrimaryEmail -> {
                _signupData.update {
                    it.copy(primaryEmail = text)
                }
            }

            TextFieldType.PrimaryPhone -> {
                _signupData.update {
                    it.copy(primaryPhone = text)
                }
            }

            TextFieldType.Password -> {
                _signupData.update {
                    it.copy(password = text)
                }
            }

            TextFieldType.Website -> {
                _signupData.update {
                    it.copy(website = text)
                }
            }
        }
    }

    fun updateOtherEmailOrPhone(text: String?, type: OtherEmailOrPhoneFields) {
        when (type) {
            OtherEmailOrPhoneFields.OtherEmail -> {
                _signupData.update {
                    it.copy(otherEmails = text)
                }
            }

            OtherEmailOrPhoneFields.OtherPhones -> {
                _signupData.update {
                    it.copy(otherPhones = text)
                }
            }
        }
    }

    fun updateEmailAndPhoneList() {
        val primaryEmail = _signupData.value.primaryEmail
        val primaryPhone = _signupData.value.primaryPhone
        emailList = mutableStateListOf(primaryEmail)
        phoneList = mutableStateListOf(primaryPhone)

        _signupData.value.otherEmails?.split(",")?.forEachIndexed { idx, item ->
            if (!item.isNullOrEmpty()) {
                emailList.add(item)
                emailListColor.add(false)
            }
        }
        _signupData.value.otherPhones?.split(",")?.forEachIndexed { idx, item ->
            if (!item.isNullOrEmpty()) {
                phoneList.add(item)
                phoneListColor.add(false)
            }

        }
    }


    fun checkFieldsValue(
        primaryEmail: String,
        primaryPhone: String,
        firstName: String,
        lastName: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        val validateEmail = checkValidEmail()
        return primaryEmail.isNotEmpty() && primaryPhone.isNotEmpty() &&
                firstName.isNotEmpty() && lastName.isNotEmpty()
                && password.isNotEmpty() && confirmPassword.isNotEmpty() && validateEmail

    }

    fun checkPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun getSignupDetails(): UserDetails {
        return _signupData.value
    }


    fun updateProfileImage(bitmap: Bitmap?) {
        _profileImage.value = bitmap

    }

    fun insertData() {
        viewModelScope.launch {
            localDBRepo.clearData()
            localDBRepo.insetIntoDb(userDetails = userDetails)
        }
    }

    fun checkValidEmail(): Boolean {
        var valid = false
        emailList.forEachIndexed { idx, item ->
            if (item.matches(regex = Regex(InputsRegex.EMAIL_VALIDATION_REGEX))) {
                valid = true
            } else {
                emailListColor[idx] = true
                return false

            }
        }
        return valid
    }

    fun checkRequiredPermission(): Boolean {
        return permissionHandler.hasRequiredPermission()
    }

    fun updateProfileImageIntoDb(bitmap: Bitmap?) {
        _signupData.value.profileImage = bitmap
    }

    private fun setContactsDetails(): UserDetails {
        if (localDBRepo.isLocalDbEmpty()) {
            return UserDetails(
                dob = "",
                age = "",
                lastName = "",
                firstName = "",
                address = "",
                primaryPhone = "",
                primaryEmail = "",
                otherPhones = null,
                otherEmails = null,
                website = "",
                password = "",
                profileImage = null

            )
        } else {

            return UserDetails(
                dob = localDBRepo.userDetails.dob,
                age = localDBRepo.userDetails.age,
                lastName = localDBRepo.userDetails.lastName,
                firstName = localDBRepo.userDetails.firstName,
                address = localDBRepo.userDetails.address,
                primaryPhone = localDBRepo.userDetails.primaryPhone,
                primaryEmail = localDBRepo.userDetails.primaryEmail,
                otherPhones = localDBRepo.userDetails.otherPhones,
                otherEmails = localDBRepo.userDetails.otherEmails,
                website = localDBRepo.userDetails.website,
                password = localDBRepo.userDetails.password,
                profileImage = localDBRepo.userDetails.profileImage

            )
        }
    }

    fun updateUIData(){
        localDBRepo.updateDBData()
    }


}