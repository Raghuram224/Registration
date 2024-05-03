package com.example.registration.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.constants.constantModals.PersonalInformation
import com.example.registration.modal.LocalDBRepo
import com.example.registration.permissionHandler.PermissionHandler
import com.example.registration.ui.theme.Blue
import com.example.registration.ui.theme.DarkGreen
import com.example.registration.ui.theme.RedBG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val localDBRepo: LocalDBRepo,
    private val permissionHandler: PermissionHandler,
) : ViewModel() {
    var currentUserId = -1
    private var _isUserIdUpdated = MutableStateFlow(false)

    private var _userDetails = MutableStateFlow(
        PersonalInformation(
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
            profileImage = null
        )
    )


    private val _uiColor = listOf(DarkGreen, RedBG, Blue).random()
    val uiColor = _uiColor
    val userDetails = _userDetails.asStateFlow()
    val isUserIdUpdated = _isUserIdUpdated.asStateFlow()


    private fun convertStringToList(text: String?): List<String>? {
        return if (text != null) {
            text.split(",")
        } else null
    }


    fun hasPhonePermission(): Boolean {
        return permissionHandler.hasRequiredPermission(permissions = permissionHandler.phonePermissions)
    }

//    private fun collectFlow() {
//        viewModelScope.launch {
//            localDBRepo.userDetailsFlow.collectLatest {
//                _userDetails.value.firstName = it.firstName
//                _userDetails.value.lastName = it.lastName
//                _userDetails.value.age = it.age
//                _userDetails.value.dob = it.dob
//                _userDetails.value.primaryEmail = it.primaryEmail
//                _userDetails.value.primaryPhone = it.primaryPhone
//                _userDetails.value.otherEmails = convertStringToList(text = it.otherEmails)
//                _userDetails.value.otherPhones = convertStringToList(text = it.otherPhones)
//                _userDetails.value.address = it.address
//                _userDetails.value.profileImage = it.profileImage
//                _userDetails.value.website  = it.website
//
//            }
//
//        }
//    }

    private fun collectFlow(userId: Int) {
        viewModelScope.launch {
            localDBRepo.userDetailsFlow(rowId = userId).collectLatest {
                Log.i("flow viewmodel userid", userId.toString())
                Log.i("flow viewmodel", it.toString())

                _userDetails.value.firstName = it.firstName
                _userDetails.value.lastName = it.lastName
                _userDetails.value.age = it.age
                _userDetails.value.dob = it.dob
                _userDetails.value.primaryEmail = it.primaryEmail
                _userDetails.value.primaryPhone = it.primaryPhone
                _userDetails.value.otherEmails = convertStringToList(text = it.otherEmails)
                _userDetails.value.otherPhones = convertStringToList(text = it.otherPhones)
                _userDetails.value.address = it.address
                _userDetails.value.profileImage = it.profileImage
                _userDetails.value.website = it.website
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("contact view model cleared")
    }

    fun updateUserId(userId: Int?) {

        if (userId != null && !_isUserIdUpdated.value) {
            Log.i("flow user id not null", userId.toString())
            currentUserId = userId
            _isUserIdUpdated.value = true
            collectFlow(userId = userId)
        }
    }


}

