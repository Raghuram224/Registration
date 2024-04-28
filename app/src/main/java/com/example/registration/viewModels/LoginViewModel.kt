package com.example.registration.viewModels

import androidx.lifecycle.ViewModel
import com.example.registration.modal.LoginRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


enum class LoginInputFields{
    Email,
    Password,
}
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: LoginRepo
) : ViewModel() {

    private val  _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val email = _email.asStateFlow()
    val passwords = _password.asStateFlow()

    fun authentication(email: String, password: String): Boolean {
        return loginRepo.validateCredentials(email = email, password = password)
    }

    fun updateLoginData(text:String,type:LoginInputFields){
        when(type){
            LoginInputFields.Email -> {
                _email.value = text
            }
            LoginInputFields.Password -> {
                _password.value = text
            }
        }
    }

}