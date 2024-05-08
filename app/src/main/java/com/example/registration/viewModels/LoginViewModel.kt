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
    var userId:String? = null
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


    fun authenticateUser(email: String, password: String): Boolean {
        localDBRepo.getAllContacts()

        val tempUserId = localDBRepo.checkUserExist(
            email = email,
            password = PasswordHash.generateHash(password = password)
        )
        if (tempUserId != null) {
            userId = tempUserId
            Log.i("Userid",userId.toString())
            return true
        }
        return false
    }

    fun checkUserType(userId:String?): Boolean {
        return if (userId != null) {
            localDBRepo.checkIsAdmin(userId = userId.toInt())
        }else false

    }




}