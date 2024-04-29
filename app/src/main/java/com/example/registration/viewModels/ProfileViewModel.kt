package com.example.registration.viewModels

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.registration.modal.LocalDBRepo
import com.example.registration.permissionHandler.PermissionHandler
import com.example.registration.view.signupScreen.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val localDBRepo: LocalDBRepo,
    private val permissionHandler: PermissionHandler,
) : ViewModel() {
    private val _signupData = MutableStateFlow(
        UserDetails(
            dob = "",
            age = "",
            lastName = "",
            firstName = "",
            otherEmails = "",
            address = "",
            primaryPhone = "",
            primaryEmail = "",
            otherPhones = "",
            password = ""

        )
    )






}