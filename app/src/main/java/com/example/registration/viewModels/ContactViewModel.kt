package com.example.registration.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.navigation.IS_ADMIN_KEY
import com.example.registration.navigation.USER_ID_KEY
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
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var currentUserId = savedStateHandle.get<Int>(USER_ID_KEY)?:-1
    var isAdmin = savedStateHandle.get<Boolean>(IS_ADMIN_KEY)?:false

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
//    val isUserIdUpdated = _isUserIdUpdated.asStateFlow()


    private fun convertStringToList(text: String?): List<String>? {
        return text?.split(",")
    }


    fun hasPhonePermission(): Boolean {
        return permissionHandler.hasRequiredPermission(permissions = permissionHandler.phonePermissions)
    }

    fun collectFlow() {
        viewModelScope.launch {
            currentUserId?.let {
                localDBRepo.userDetailsFlow(rowId = it).collectLatest {
                    Log.i("flow viewmodel userid", currentUserId.toString())
                    Log.i("flow viewmodel", it.toString())

                    _userDetails.value.apply {
                        firstName = it.firstName
                        lastName = it.lastName
                        age = it.age
                        dob = it.dob
                        primaryEmail = it.primaryEmail
                        primaryPhone = it.primaryPhone
                        otherEmails = convertStringToList(text = it.otherEmails)
                        otherPhones = convertStringToList(text = it.otherPhones)
                        address = it.address
                        profileImage = it.profileImage
                        website = it.website
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("contact view model cleared")
    }

//    fun updateUserDetails(userId: Int?, isAdmin: Boolean?) {
//
//        if (userId != null && !_isUserIdUpdated.value && isAdmin != null) {
//            currentUserId = userId
//            _isUserIdUpdated.value = true
//            this.isAdmin = isAdmin
//            collectFlow(userId = userId)
//        }
//    }


}

