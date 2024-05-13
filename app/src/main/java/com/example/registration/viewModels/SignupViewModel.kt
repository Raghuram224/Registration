package com.example.registration.viewModels

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.constants.InputsRegex
import com.example.registration.constants.constantModals.FieldsColor
import com.example.registration.constants.constantModals.InputListTypes
import com.example.registration.constants.constantModals.OtherEmailOrPhoneFields
import com.example.registration.constants.constantModals.SignupFieldsColorType
import com.example.registration.constants.constantModals.TextFieldType
import com.example.registration.constants.constantModals.UserDetails
import com.example.registration.modal.LocalDBRepo
import com.example.registration.navigation.USER_ID_KEY
import com.example.registration.permissionHandler.PermissionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val localDBRepo: LocalDBRepo,
    private val permissionHandler: PermissionHandler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val currentUserId = savedStateHandle.get<String>(USER_ID_KEY)
    val numberOfEmailAndPhonesAllowed = 5

    private val _emailList = mutableStateListOf<String>("")
    private val _phoneList = mutableStateListOf<String>("")

    private var _userDetails = MutableStateFlow(
        UserDetails(
            dob = "",
            age = "",
            lastName = "",
            firstName = "",
            address = "",
            primaryPhone = "",
            primaryEmail = "",
            otherPhones = listOf(),
            otherEmails = listOf(),
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
            confirmPasswordColor = false,
            primaryPhoneColor = false
        )
    )

    private var _profileImage = MutableStateFlow<Bitmap?>(_userDetails.value.profileImage)

    val userDetails = _userDetails.asStateFlow()
    val profileImage = _profileImage.asStateFlow()
    val emailListColor = mutableStateListOf(false)
    val fieldsColor = _fieldsColor.asStateFlow()

    val emailList: List<String> = _emailList
    val phoneList: List<String> = _phoneList


    private fun updateOtherEmailOrPhoneList(
        inputList: List<String>,
        idx: Int,
        type: OtherEmailOrPhoneFields
    ) {

        if (inputList.isNotEmpty()) {
            when (type) {

                OtherEmailOrPhoneFields.OtherEmail -> {
                    val newList = arrayListOf<String>()

                    inputList.forEachIndexed { index, item ->
                        if (index != idx && item.isNotEmpty()) {
                            newList.add(item.trim())
                        }
                    }
                    _userDetails.value.otherEmails = newList
                }

                OtherEmailOrPhoneFields.OtherPhones -> {
                    val newList = arrayListOf<String>()

                    inputList.forEachIndexed { index, item ->
                        if (index != idx && item.isNotEmpty()) {
                            newList.add(item.trim())
                        }
                    }
                    _userDetails.value.otherPhones = newList
                }
            }

        }


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

    fun updateEmailOrPhoneList(text: String, idx: Int, type: InputListTypes) {
        when (type) {
            InputListTypes.Email -> {
                _emailList[idx] = text
            }

            InputListTypes.Phone -> {
                _phoneList[idx] = text
            }
        }
    }

    fun addFieldsOfEmailOrPhoneList(text: String = "", type: InputListTypes) {
        when (type) {
            InputListTypes.Email -> {
                _emailList.add(text)
            }

            InputListTypes.Phone -> {
                _phoneList.add(text)
            }
        }

    }

    fun removeFieldsOfEmailOrPhoneList(idx: Int, type: InputListTypes) {
        Log.i("fields email ${emailList.size}", emailList.toList().toString())

        when (type) {
            InputListTypes.Email -> {
                _emailList.removeAt(idx)
            }

            InputListTypes.Phone -> {
                _phoneList.removeAt(idx)
            }
        }

    }

    fun checkPassword(password: String, confirmPassword: String): Boolean {
        val isValidPassword = password == confirmPassword

        updateFieldsColor(isError = !isValidPassword, SignupFieldsColorType.ConfirmPassword)
        updateFieldsColor(isError = !isValidPassword, SignupFieldsColorType.Password)
        return isValidPassword
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
        _emailList.forEachIndexed { idx, item ->
            if (!item.matches(regex = Regex(InputsRegex.EMAIL_VALIDATION_REGEX))) {
                emailListColor[idx] = true
                return false
            }
        }
        return true
    }

    fun checkRequiredCameraPermission(): Boolean {
        return permissionHandler.hasRequiredPermission(permissionHandler.cameraPermissions)
    }

    fun updateProfileImageIntoUserDetails(bitmap: Bitmap?) {
        _userDetails.value.profileImage = bitmap
    }

    fun updateFieldsColor(isError: Boolean, type: SignupFieldsColorType) {
        when (type) {
            SignupFieldsColorType.FName -> {
                _fieldsColor.value.fNameColor = isError

            }

            SignupFieldsColorType.LName -> {
                _fieldsColor.value.lNameColor = isError

            }

            SignupFieldsColorType.Password -> {
                _fieldsColor.value.passwordColor = isError

            }

            SignupFieldsColorType.ConfirmPassword -> {
                _fieldsColor.value.confirmPasswordColor = isError

            }

            SignupFieldsColorType.PrimaryPhone -> {
                _fieldsColor.value.primaryPhoneColor = isError
            }
        }

    }

    fun checkFieldsValue(
        primaryEmail: String,
        primaryPhoneIndex: Int,
        firstName: String,
        lastName: String,
        password: String,
        confirmPassword: String,

        ): Boolean {

        return primaryEmail.isNotEmpty() &&
                firstName.isNotEmpty() && lastName.isNotEmpty()
                && password.isNotEmpty() && confirmPassword.isNotEmpty() && checkValidEmail()
                && checkPrimaryPhoneValue(primaryPhoneIndex = primaryPhoneIndex)

    }

    private fun checkPrimaryPhoneValue(primaryPhoneIndex: Int): Boolean {
        return if (phoneList.size>1) {
            if (phoneList[primaryPhoneIndex].isEmpty()) {
                updateFieldsColor(isError = true, SignupFieldsColorType.PrimaryPhone)
                false
            } else {
                updateFieldsColor(isError = false, SignupFieldsColorType.PrimaryPhone)
                true
            }
        } else {
            true
        }


    }

    fun updateRequiredFieldsColor(
        primaryEmailIndex: Int,
        confirmPassword: String
    ) {
        emailListColor[primaryEmailIndex] =
            _emailList[primaryEmailIndex].isEmpty()

        updateFieldsColor(
            isError = _userDetails.value.firstName.isEmpty(),
            SignupFieldsColorType.FName
        )
        updateFieldsColor(
            isError = _userDetails.value.lastName.isEmpty(),
            SignupFieldsColorType.LName
        )
        updateFieldsColor(
            isError = _userDetails.value.password.isEmpty(),
            SignupFieldsColorType.Password
        )
        updateFieldsColor(
            isError = confirmPassword.isEmpty(),
            SignupFieldsColorType.ConfirmPassword
        )
    }

    fun updateFieldsValuesToUserDetails(
        primaryEmailIndex: Int,
        primaryPhoneIndex: Int,

        ) {

        updateOtherEmailOrPhoneList(
            inputList = emailList,
            primaryEmailIndex,
            OtherEmailOrPhoneFields.OtherEmail
        )
        updateOtherEmailOrPhoneList(
            inputList = phoneList,
            primaryPhoneIndex,
            OtherEmailOrPhoneFields.OtherPhones
        )

        updateUserData(
            text = emailList[primaryEmailIndex].trim(),
            type = TextFieldType.PrimaryEmail
        )
        updateUserData(
            text = phoneList[primaryPhoneIndex].trim(),
            TextFieldType.PrimaryPhone
        )

    }

    fun isEmailAlreadyTaken(email: String): Boolean {
        return !localDBRepo.checkEmailIdAvailable(email = email) //returns true if the user doesn't exist
    }

    @SuppressLint("SimpleDateFormat")
    fun convertMillisToDate(mill: Long?): String {
        val format = SimpleDateFormat("dd/MM/yyyy")

        return if (mill != null) format.format(Date(mill)) else ""
    }

    fun milliToYears(milliseconds: Long): String {
        val totalSeconds = milliseconds / 1000
        val minutes = totalSeconds / 60
        val hour = minutes / 60
        val day = hour / 24
        val year = (day / 365)

        return year.toString()
    }

    fun yearsToMillis(years: Long): Long {
        val days = years * 365
        val hours = days * 24
        val minutes = hours * 60
        val seconds = minutes * 60
        return seconds * 1000
    }

    @SuppressLint("SimpleDateFormat")
    fun isUserAgeAndDobMatch(): Boolean {

        return if (userDetails.value.age.isNotEmpty()) {

            val age = _userDetails.value.age.trim().toInt()
            return if (age <= 0) false else {
                val givenAgeDob =
                    convertMillisToDate(mill = Date().time.minus(yearsToMillis(years = age.toLong()))).split(
                        "/"
                    )

                val selectedCalendarDob = if (_userDetails.value.dob.isNotEmpty()) {
                    _userDetails.value.dob.split("/")
                } else {
                    SimpleDateFormat("dd//MM/yyyy").format(Date()).split("/")
                }

                givenAgeDob[2] == selectedCalendarDob[2]
            }

        } else {
            userDetails.value.dob.isEmpty()

        }


    }


    fun setContactsDetails() {
        if (currentUserId != null) {
            val userDetails = localDBRepo.getUserDetails(userId = currentUserId.toInt())

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

            setEmailAndPhones()
        }


    }

    private fun setEmailAndPhones() {
        updateEmailOrPhoneList(
            text = _userDetails.value.primaryEmail,
            idx = 0,
            InputListTypes.Email
        )
        updateEmailOrPhoneList(
            text = _userDetails.value.primaryPhone,
            idx = 0,
            InputListTypes.Phone
        )

        _profileImage.value = _userDetails.value.profileImage

        setOtherEmailsOrPhones(
            list = _userDetails.value.otherEmails,
            type = InputListTypes.Email
        )
        setOtherEmailsOrPhones(
            list = _userDetails.value.otherPhones,
            type = InputListTypes.Phone
        )

    }

    private fun setOtherEmailsOrPhones(list: List<String>?, type: InputListTypes) {

        if (!list.isNullOrEmpty()) {
            list.let { item ->
                item.forEach {
                    addFieldsOfEmailOrPhoneList(text = it, type)
                    emailListColor.add(false)
                }
            }

        }

    }


    fun updateDBData() {
        viewModelScope.launch {
            if (currentUserId != null) {

                localDBRepo.updateUserDetails(
                    userDetails = _userDetails.value,
                    currentUserSID = currentUserId.toInt()
                )

            }
        }
    }

}

