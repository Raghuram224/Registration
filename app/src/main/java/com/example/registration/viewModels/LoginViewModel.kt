package com.example.registration.viewModels

import androidx.lifecycle.ViewModel
import com.example.registration.modal.LocalDBRepo
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
    private val localDBRepo: LocalDBRepo
) : ViewModel() {

    private val  _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val email = _email.asStateFlow()
    val passwords = _password.asStateFlow()



    fun authenticateEmail(email: String):Boolean{
        return  localDBRepo.validateEmail(email = email)
    }
    fun  authenticatePassword(password:String):Boolean{
        return localDBRepo.validatePassword(password = password)
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