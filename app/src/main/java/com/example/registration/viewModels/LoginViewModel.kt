package com.example.registration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.modal.LocalDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class LoginInputFields {
    Email,
    Password,
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localDBRepo: LocalDBRepo
) : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val email = _email.asStateFlow()
    val passwords = _password.asStateFlow()


    fun authenticateEmail(email: String): Boolean {
        return email == localDBRepo.userDetails.primaryEmail

    }

    fun authenticatePassword(password: String): Boolean {
        return password == localDBRepo.userDetails.password
    }

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

    fun clearData() {
        viewModelScope.launch {
            localDBRepo.clearData()
//            localDBRepo.updateDBData()
        }

    }


}