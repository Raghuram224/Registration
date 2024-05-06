package com.example.registration.viewModels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.constants.constantModals.OtherEmailOrPhoneFields
import com.example.registration.constants.constantModals.TextFieldType
import com.example.registration.constants.constantModals.UserDetails
import com.example.registration.constants.InputsRegex
import com.example.registration.modal.LocalDBRepo
import com.example.registration.permissionHandler.PermissionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject





@HiltViewModel
class SignupViewModel @Inject constructor(
    private val localDBRepo: LocalDBRepo,
    private val permissionHandler: PermissionHandler
) : ViewModel() {

    private val _signupData = MutableStateFlow(
        UserDetails(
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
    )

    private var _profileImage = MutableStateFlow<Bitmap?>(_signupData.value.profileImage)

    val signupData = _signupData.asStateFlow()
    var emailList = mutableStateListOf("")
    var phoneList = mutableStateListOf("")
    val profileImage = _profileImage.asStateFlow()
    val emailListColor = mutableStateListOf(false)
//    val phoneListColor = mutableStateListOf(false)

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

    fun checkFieldsValue(
        primaryEmail: String,
        firstName: String,
        lastName: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        val validateEmail = checkValidEmail()
        return primaryEmail.isNotEmpty()  &&
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
            localDBRepo.insetIntoDb(userDetails = _signupData.value)
        }
    }

    private fun checkValidEmail(): Boolean {
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
        return permissionHandler.hasRequiredPermission(permissionHandler.cameraPermissions)
    }

    fun updateProfileImageIntoDb(bitmap: Bitmap?) {
        _signupData.value.profileImage = bitmap
    }


}