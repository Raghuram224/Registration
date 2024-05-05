package com.example.registration.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.registration.constants.PasswordHash
import com.example.registration.constants.constantModals.LoginInputFields
import com.example.registration.constants.constantModals.UserType
import com.example.registration.modal.LocalDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localDBRepo: LocalDBRepo
) : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val email = _email.asStateFlow()
    val passwords = _password.asStateFlow()
    var userId = 0
    fun updateLoginData(text: String, type: LoginInputFields) {

        when (type) {
            LoginInputFields.Email -> {
                _email.value = text
            }

            LoginInputFields.Password -> {
                _password.value = text
            }
        }
    }

//    fun validateCredentials(email: String, password: String): CredentialsValidationStatus {
//
//        return localDBRepo.validateCredentials(
//            email = email,
//            password = PasswordHash.generateHash(password)
//        )
//    }

//    fun validateCredentials(email: String, password: String): CredentialsValidationStatus {
//
//        val status = when (email == currentUserDetails.primaryEmail) {
//            true -> {
//                if (password == currentUserDetails.password) {
//                    CredentialsValidationStatus.ValidCredentials
//                } else {
//                    CredentialsValidationStatus.PasswordError
//                }
//            }
//
//            false -> {
//                CredentialsValidationStatus.EmailError
//            }
//        }
//
//        return status
//    }

    fun authenticateUser(email: String, password: String): Boolean {
        localDBRepo.getAllContacts()

        val tempUserId = localDBRepo.checkUserExist(
            email = email,
            password = PasswordHash.generateHash(password = password)
        )
        if (tempUserId != -1) {
            userId = tempUserId
            Log.i("Userid",userId.toString())
            return true
        }
        return false
    }

    fun checkUserType(userId:Int): UserType {
        return if (localDBRepo.checkIsAdmin(userId = userId)) {
            UserType.Admin
        }else UserType.Client
    }




}