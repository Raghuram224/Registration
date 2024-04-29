package com.example.registration.viewModels

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.registration.constants.InputsRegex
import com.example.registration.modal.LocalDBRepo
import com.example.registration.permissionHandler.PermissionHandler

import com.example.registration.view.signupScreen.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


enum class TextFieldType {
    FirstName,
    LastName,
    Age,
    Address,
    DOB,
    PrimaryEmail,
    PrimaryPhone,
    OtherEmails,
    OtherPhones,
    Password,


}


@HiltViewModel
class SignupViewModel @Inject constructor(
   private val  localDBRepo: LocalDBRepo,
    private val permissionHandler: PermissionHandler
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
            password= ""

        )
    )
    private val _capturedImage= MutableStateFlow<Bitmap?>(null)
    private var _selectedImage =MutableStateFlow<Uri?>(null)
    private var _selectedImageType = MutableStateFlow(0)


    val signupData = _signupData.asStateFlow()
    val emailList = mutableStateListOf("")
    val phoneList = mutableStateListOf("")
    val capturedImage = _capturedImage.asStateFlow()
    val selectedImageType =  _selectedImageType.asStateFlow()
    val selectedImage = _selectedImage.asStateFlow()
    val emailListColor = mutableStateListOf(false)
    val phoneListColor = mutableStateListOf(false)

    lateinit var userDetails: UserDetails




    fun convertListToString(list: List<String>, idx: Int): String {
        var str = ""
        list.forEachIndexed { index, item ->
            if (index != idx) {
                str += "$item,"
            }

        }
        return str
    }




    fun updateSignupData(text: String, type: TextFieldType) {
        when (type) {
            TextFieldType.FirstName -> {
               _signupData.update {
                   it.copy(firstName = text)
               }
            }

            TextFieldType.LastName -> {
                _signupData.update {
                    it.copy(lastName = text)
                }
            }

            TextFieldType.Age -> {
                _signupData.update {
                    it.copy(age = text)
                }
            }

            TextFieldType.Address -> {
                _signupData.update {
                    it.copy(address = text)
                }
            }

            TextFieldType.DOB -> {
                _signupData.update {
                    it.copy(dob = text)
                }
            }

            TextFieldType.PrimaryEmail -> {
                _signupData.update {
                    it.copy(primaryEmail = text)
                }
            }
            TextFieldType.PrimaryPhone -> {
                _signupData.update {
                    it.copy(primaryPhone = text)
                }
            }
            TextFieldType.OtherEmails ->{
                _signupData.update {
                    it.copy(otherEmails = text)
                }
            }
            TextFieldType.OtherPhones -> {
                _signupData.update {
                    it.copy(otherPhones = text)
                }
            }

            TextFieldType.Password -> {
                _signupData.update {
                    it.copy(password = text)
                }
            }
        }
    }


    fun checkFieldsValue(
        primaryEmail: String,
        primaryPhone: String,
        firstName: String,
        lastName: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        val validateEmail = checkValidEmail()
        return primaryEmail.isNotEmpty() && primaryPhone.isNotEmpty() &&
                firstName.isNotEmpty() && lastName.isNotEmpty()
                && password.isNotEmpty() && confirmPassword.isNotEmpty() && validateEmail

    }

    fun checkPassword(password: String, confirmPassword: String): Boolean {
        return  password == confirmPassword
    }

    fun getSignupDetails():UserDetails{
        return _signupData.value
    }

    fun updateCapturedImage(bitmap: Bitmap){
        _capturedImage.value =bitmap
    }

    fun updateSelectedImageType(idx: Int){
        _selectedImageType.value =idx
    }
    fun updateSelectedImage(uri: Uri?){
        _selectedImage.value =uri
    }

    fun insertData(){
        localDBRepo.clearData()
        localDBRepo.insetIntoDb(userDetails = userDetails)
    }
    fun checkValidEmail():Boolean{
        var valid =false
        emailList.forEachIndexed{ idx,item->
            if (item.matches(regex = Regex(InputsRegex.EMAIL_VALIDATION_REGEX))) {
                valid = true
            } else {
                emailListColor[idx] = true
                return  false

            }
        }
        return  valid
    }

    fun checkRequiredPermission():Boolean{
        return  permissionHandler.hasRequiredPermission()
    }










}