package com.example.registration.viewModels

import androidx.lifecycle.ViewModel
import com.example.registration.constants.PasswordHash
import com.example.registration.constants.constantModals.LoginInputFields
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



//    fun authenticateEmail(email: String): Boolean {
//        localDBRepo.currentUserDetails
//        return email == localDBRepo.authenticateUserDetails.primaryEmail
//
//    }
//
//    fun authenticatePassword(password: String): Boolean {
//        return password == localDBRepo.authenticateUserDetails.password
//    }

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

    fun authenticateEmail(email: String):Boolean{

        return localDBRepo.authenticateEmail(email)
    }
    fun authenticatePassword(password: String):Boolean{
        return  localDBRepo.authenticatePassword( password = PasswordHash.generateHash(password))
    }






}