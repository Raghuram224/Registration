package com.example.registration.viewModels

import android.graphics.Bitmap
import android.util.Log
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
class EditContactsViewmodel @Inject constructor(
    private val localDBRepo: LocalDBRepo,
    private val permissionHandler: PermissionHandler
) : ViewModel() {

    private var currentUserId = -1
    private val _contactData = MutableStateFlow(
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
            profileImage = null,
            password = ""

        )
    )
    private var _isUserIdUpdated = MutableStateFlow(false)

    private var _profileImage = MutableStateFlow<Bitmap?>(_contactData.value.profileImage)
    private var _isDataLoaded = MutableStateFlow(false)

    var contactData = _contactData.asStateFlow()
    var emailList = mutableStateListOf(_contactData.value.primaryEmail)
    var phoneList = mutableStateListOf(_contactData.value.primaryPhone)
    val profileImage = _profileImage.asStateFlow()
    val emailListColor = mutableStateListOf(false)
    val phoneListColor = mutableStateListOf(false)
    val isDataLoaded = _isDataLoaded.asStateFlow()

    val isUserIdUpdated = _isUserIdUpdated.asStateFlow()


    fun convertListToString(list: List<String>, idx: Int): String? {
        var str = ""
        list.forEachIndexed { index, item ->
            if (index != idx && item.isNotEmpty()) {
                str += "$item,"
            }

        }
        return if (str.isNotEmpty()) str else null
    }


    fun updateContactData(text: String, type: TextFieldType) {
        when (type) {
            TextFieldType.FirstName -> {
                _contactData.update {
                    it.copy(firstName = text)
                }
            }

            TextFieldType.LastName -> {
                _contactData.update {
                    it.copy(lastName = text)
                }
            }

            TextFieldType.Age -> {
                _contactData.update {
                    it.copy(age = text)
                }
            }

            TextFieldType.Address -> {
                _contactData.update {
                    it.copy(address = text)
                }
            }

            TextFieldType.DOB -> {
                _contactData.update {
                    it.copy(dob = text)
                }
            }

            TextFieldType.PrimaryEmail -> {
                _contactData.update {
                    it.copy(primaryEmail = text)
                }
            }

            TextFieldType.PrimaryPhone -> {
                _contactData.update {
                    it.copy(primaryPhone = text)
                }
            }

            TextFieldType.Password -> {
                _contactData.update {
                    it.copy(password = text)
                }
            }

            TextFieldType.Website -> {
                _contactData.update {
                    it.copy(website = text)
                }
            }
        }
    }

    fun updateOtherEmailOrPhone(text: String?, type: OtherEmailOrPhoneFields) {
        when (type) {
            OtherEmailOrPhoneFields.OtherEmail -> {
                _contactData.update {
                    it.copy(otherEmails = text)
                }
            }

            OtherEmailOrPhoneFields.OtherPhones -> {
                _contactData.update {
                    it.copy(otherPhones = text)
                }
            }
        }
    }


    fun checkFieldsValue(
        primaryEmail: String,
        primaryPhone: String,
        firstName: String,
        lastName: String,

        ): Boolean {

        val validateEmail = checkValidEmail()
        return primaryEmail.isNotEmpty() && primaryPhone.isNotEmpty() &&
                firstName.isNotEmpty() && lastName.isNotEmpty()
                && validateEmail

    }


    fun updateProfileImage(bitmap: Bitmap?) {
        _profileImage.value = bitmap

    }

    fun updateData() {
        viewModelScope.launch {
            localDBRepo.updateUserDetails(userDetails = _contactData.value, currentUserSID =  currentUserId)
        }
    }

    private fun checkValidEmail(): Boolean {
        var valid = false
        emailList.forEachIndexed { idx, item ->
            if (item != null) {
                if (item.matches(regex = Regex(InputsRegex.EMAIL_VALIDATION_REGEX))) {
                    valid = true
                } else {
                    emailListColor[idx] = true
                    return false

                }
            }
        }
        return valid
    }

    private fun convertStringToList(text: String?): List<String>? {
        return text?.split(",")
    }

    fun checkRequiredPermission(): Boolean {
        return permissionHandler.hasRequiredPermission(permissionHandler.cameraPermissions)
    }

    fun updateProfileImageIntoDb(bitmap: Bitmap?) {
        _contactData.value.profileImage = bitmap
    }

    private fun setContactsDetails(userId: Int) {
        val userDetails = localDBRepo.getUserDetails(userId = userId)
        _contactData.value.dob = userDetails.dob
        _contactData.value.age = userDetails.age
        _contactData.value.lastName = userDetails.lastName
        _contactData.value.firstName = userDetails.firstName
        _contactData.value.address = userDetails.address
        _contactData.value.primaryPhone = userDetails.primaryPhone
        _contactData.value.primaryEmail = userDetails.primaryEmail
        _contactData.value.otherPhones = userDetails.otherPhones
        _contactData.value.otherEmails = userDetails.otherEmails
        _contactData.value.website = userDetails.website
        _contactData.value.profileImage = userDetails.profileImage
        _contactData.value.password = userDetails.password

        Log.i("contact obj",_contactData.value.toString())
        loadEmailAndOtherPhones()


    }

    private fun loadEmailAndOtherPhones() {
        emailList[0]= contactData.value.primaryEmail
        phoneList[0]=contactData.value.primaryPhone
        _profileImage.value = contactData.value.profileImage
        convertStringToList(contactData.value.otherEmails)?.forEach {
            if (it.isNotEmpty()) {
                emailList.add(it)
                emailListColor.add(false)


            }

        }
        convertStringToList(contactData.value.otherPhones)?.forEach {
            if (it.isNotEmpty()) {
                phoneList.add(it)
                phoneListColor.add(false)
            }

        }
        Log.i("contact obj",_contactData.value.toString())
        Log.i("contact data",_contactData.value.toString())


    }

    fun updateUserId(userId: Int?) {
        Log.i("flow out",userId.toString())
        if (userId != null && !_isUserIdUpdated.value) {
            currentUserId = userId
            _isUserIdUpdated.value = true
            setContactsDetails(userId = userId)
            Log.i("flow in",userId.toString())

        }
    }


}
