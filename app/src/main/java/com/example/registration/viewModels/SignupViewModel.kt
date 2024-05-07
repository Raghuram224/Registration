package com.example.registration.viewModels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.constants.constantModals.OtherEmailOrPhoneFields
import com.example.registration.constants.constantModals.TextFieldType
import com.example.registration.constants.constantModals.UserDetails
import com.example.registration.constants.InputsRegex
import com.example.registration.constants.constantModals.FieldsColor
import com.example.registration.constants.constantModals.SignupFieldsColorType
import com.example.registration.modal.LocalDBRepo
import com.example.registration.navigation.USER_ID_KEY
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
    private val permissionHandler: PermissionHandler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val currentUserId = savedStateHandle.get<Int>(USER_ID_KEY) ?: -1

    private var _userDetails = MutableStateFlow(
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

    private val _fieldsColor = MutableStateFlow(
        FieldsColor(
            fNameColor = false,
            lNameColor = false,
            passwordColor = false,
            confirmPasswordColor = false
        )
    )

    private var _profileImage = MutableStateFlow<Bitmap?>(_userDetails.value.profileImage)

    val signupData = _userDetails.asStateFlow()
    var emailList = mutableStateListOf("")
    var phoneList = mutableStateListOf("")
    val profileImage = _profileImage.asStateFlow()
    val emailListColor = mutableStateListOf(false)
    val fieldsColor = _fieldsColor.asStateFlow()



    private fun convertListToString(list: List<String>, idx: Int): String? {
        var str = ""
        list.forEachIndexed { index, item ->
            if (index != idx && item.isNotEmpty()) {
                str += "$item,"
            }

        }
        return if (str.isNotEmpty()) str else null
    }


    fun updateUserData(text: String, type: TextFieldType) {
        when (type) {
            TextFieldType.FirstName -> {
                _userDetails.update {
                    it.copy(firstName = text)
                }
            }

            TextFieldType.LastName -> {
                _userDetails.update {
                    it.copy(lastName = text)
                }
            }

            TextFieldType.Age -> {
                _userDetails.update {
                    it.copy(age = text)
                }
            }

            TextFieldType.Address -> {
                _userDetails.update {
                    it.copy(address = text)
                }
            }

            TextFieldType.DOB -> {
                _userDetails.update {
                    it.copy(dob = text)
                }
            }

            TextFieldType.PrimaryEmail -> {
                _userDetails.update {
                    it.copy(primaryEmail = text)
                }
            }

            TextFieldType.PrimaryPhone -> {
                _userDetails.update {
                    it.copy(primaryPhone = text)
                }
            }

            TextFieldType.Password -> {
                _userDetails.update {
                    it.copy(password = text)
                }
            }

            TextFieldType.Website -> {
                _userDetails.update {
                    it.copy(website = text)
                }
            }
        }
    }

    private fun updateOtherEmailOrPhone(text: String?, type: OtherEmailOrPhoneFields) {
        when (type) {
            OtherEmailOrPhoneFields.OtherEmail -> {
                _userDetails.update {
                    it.copy(otherEmails = text)
                }
            }

            OtherEmailOrPhoneFields.OtherPhones -> {
                _userDetails.update {
                    it.copy(otherPhones = text)
                }
            }
        }
    }


    fun checkPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }


    fun updateProfileImage(bitmap: Bitmap?) {
        _profileImage.value = bitmap

    }

    fun insertData() {
        viewModelScope.launch {
            localDBRepo.insetIntoDb(userDetails = _userDetails.value)
        }
    }

    private fun checkValidEmail(): Boolean {
        emailList.forEachIndexed { idx, item ->
            if (!item.matches(regex = Regex(InputsRegex.EMAIL_VALIDATION_REGEX))) {
                emailListColor[idx] = true
                return false
            }
        }
        return true
    }

    fun checkRequiredPermission(): Boolean {
        return permissionHandler.hasRequiredPermission(permissionHandler.cameraPermissions)
    }

    fun updateProfileImageIntoUserDetails(bitmap: Bitmap?) {
        _userDetails.value.profileImage = bitmap
    }

    private fun updateFieldsColor(isValid: Boolean, type: SignupFieldsColorType) {
        when (type) {
            SignupFieldsColorType.FName -> {
                _fieldsColor.value.fNameColor = isValid

            }

            SignupFieldsColorType.LName -> {
                _fieldsColor.value.lNameColor = isValid

            }

            SignupFieldsColorType.Password -> {
                _fieldsColor.value.passwordColor = isValid

            }

            SignupFieldsColorType.ConfirmPassword -> {
                _fieldsColor.value.confirmPasswordColor = isValid

            }
        }

    }

    fun setContactsDetails() {
        val userDetails = localDBRepo.getUserDetails(userId = currentUserId)

        _userDetails.value.apply {
            dob = userDetails.dob
            age = userDetails.age
            lastName = userDetails.lastName
            firstName = userDetails.firstName
            address = userDetails.address
            primaryPhone = userDetails.primaryPhone
            primaryEmail = userDetails.primaryEmail
            otherPhones = userDetails.otherPhones
            otherEmails = userDetails.otherEmails
            website = userDetails.website
            profileImage = userDetails.profileImage
            password = userDetails.password
        }

        loadEmailAndOtherPhones()


    }

    private fun loadEmailAndOtherPhones() {
        emailList[0] = _userDetails.value.primaryEmail
        phoneList[0] = _userDetails.value.primaryPhone
        _profileImage.value = _userDetails.value.profileImage
        convertStringToList(_userDetails.value.otherEmails)?.forEach {
            if (it.isNotEmpty()) {
                emailList.add(it)
                emailListColor.add(false)


            }

        }
        convertStringToList(_userDetails.value.otherPhones)?.forEach {
            if (it.isNotEmpty()) {
                phoneList.add(it)
            }

        }


    }

    private fun convertStringToList(text: String?): List<String>? {
        return text?.split(",")
    }

    fun updateDBData() {
        viewModelScope.launch {
            localDBRepo.updateUserDetails(
                userDetails = _userDetails.value,
                currentUserSID = currentUserId
            )
        }
    }

    fun checkFieldsValue(
        primaryEmail: String,
        firstName: String,
        lastName: String,
        password: String,
        confirmPassword: String,

        ): Boolean {

        return primaryEmail.isNotEmpty() &&
                firstName.isNotEmpty() && lastName.isNotEmpty()
                && password.isNotEmpty() && confirmPassword.isNotEmpty() && checkValidEmail()

    }

    fun updateRequiredFieldsColor(
        primaryEmailIndex: Int,
        confirmPassword: String
    ) {
        emailListColor[primaryEmailIndex] =
            emailList[primaryEmailIndex].isEmpty()

        updateFieldsColor(
            isValid = _userDetails.value.firstName.isEmpty(),
            SignupFieldsColorType.FName
        )
        updateFieldsColor(
            isValid = _userDetails.value.lastName.isEmpty(),
            SignupFieldsColorType.LName
        )
        updateFieldsColor(
            isValid = _userDetails.value.password.isEmpty(),
            SignupFieldsColorType.Password
        )
        updateFieldsColor(
            isValid = confirmPassword.isEmpty(),
            SignupFieldsColorType.ConfirmPassword
        )
    }

    fun updateFieldsValuesToUserDetails(
        primaryEmailIndex: Int,
        primaryPhoneIndex: Int,

        ) {
        val otherEmails = convertListToString(list = emailList, idx = primaryEmailIndex)
        val otherPhones = convertListToString(list = phoneList, idx = primaryPhoneIndex)

        updateOtherEmailOrPhone(
            text = otherEmails,
            type = OtherEmailOrPhoneFields.OtherEmail
        )
        updateOtherEmailOrPhone(
            text = otherPhones,
            type = OtherEmailOrPhoneFields.OtherPhones
        )

        updateUserData(
            text = emailList[primaryEmailIndex],
            type = TextFieldType.PrimaryEmail
        )
        updateUserData(
            text = phoneList[primaryPhoneIndex],
            TextFieldType.PrimaryPhone
        )

    }


}

