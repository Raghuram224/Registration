package com.example.registration.viewModels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.registration.view.signupScreen.SignupDetails
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


}
enum class TextFieldTypeTest {
    FirstName,
    LastName,
    Age,
    Address,
    DOB,
    PrimaryEmail,
    PrimaryPhone,
    OtherEmails,
    OtherPhones,


}


@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {

    private var _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    private var _selectedCameraImage = MutableStateFlow<Bitmap?>(null)
    private val _firstName = MutableStateFlow("")
    private val _age = MutableStateFlow("0")
    private val _address = MutableStateFlow("")
    private val _dob = MutableStateFlow("")
    private val _lastName = MutableStateFlow("")
    private val _signupData = MutableStateFlow(
        SignupDetails(
            dob = "",
            age = "",
            lastName = "",
            firstName = "",
            otherEmails = "",
            address = "",
            primaryPhone = "",
            primaryEmail = "",
            otherPhones = ""
        )
    )

    val firstName = _firstName.asStateFlow()
    val lastName = _lastName.asStateFlow()
    val age = _age.asStateFlow()
    val address = _address.asStateFlow()
    val dob = _dob.asStateFlow()
    val signupData = _signupData.asStateFlow()


    val emailList = mutableStateListOf("")
    val phoneList = mutableStateListOf("")


    lateinit var signupDetails: SignupDetails

    val bitmaps = _bitmaps.asStateFlow()
    val selectedCameraImage = _selectedCameraImage.asStateFlow()


    fun takePhoto(bitmap: Bitmap) {
        _bitmaps.value += bitmap
    }

    fun selectCameraImage(bitmap: Bitmap) {
        _selectedCameraImage.value = bitmap
    }

    fun convertListToString(list: List<String>, idx: Int): String {
        var str = ""
        list.forEachIndexed { index, item ->
            if (index != idx) {
                str += "$item,"
            }

        }
        return str
    }

    fun updateText(text: String, type: TextFieldType) {
        when (type) {
            TextFieldType.FirstName -> {
                _firstName.value = text
            }

            TextFieldType.LastName -> {
                _lastName.value = text
            }

            TextFieldType.Age -> {
                _age.value = text
            }

            TextFieldType.Address -> {
                _address.value = text
            }

            TextFieldType.DOB -> {
                _dob.value = text
            }


        }
    }


    fun updateSignupDataTest(text: String, type: TextFieldTypeTest) {
        when (type) {
            TextFieldTypeTest.FirstName -> {
               _signupData.update {
                   it.copy(firstName = text)
               }
            }

            TextFieldTypeTest.LastName -> {
                _signupData.update {
                    it.copy(lastName = text)
                }
            }

            TextFieldTypeTest.Age -> {
                _signupData.update {
                    it.copy(age = text)
                }
            }

            TextFieldTypeTest.Address -> {
                _signupData.update {
                    it.copy(address = text)
                }
            }

            TextFieldTypeTest.DOB -> {
                _signupData.update {
                    it.copy(dob = text)
                }
            }

            TextFieldTypeTest.PrimaryEmail -> {
                _signupData.update {
                    it.copy(primaryEmail = text)
                }
            }
            TextFieldTypeTest.PrimaryPhone -> {
                _signupData.update {
                    it.copy(primaryPhone = text)
                }
            }
            TextFieldTypeTest.OtherEmails ->{
                _signupData.update {
                    it.copy(otherEmails = text)
                }
            }
            TextFieldTypeTest.OtherPhones -> {
                _signupData.update {
                    it.copy(otherPhones = text)
                }
            }
        }
    }



    fun checkFieldsValue(
        primaryEmail: String,
        primaryPhone: String,
        age: String,
        dob: String,
        firstName: String,
        lastName: String,
    ): Boolean {


        return primaryEmail.isNotEmpty() && primaryPhone.isNotEmpty() &&
                firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty()
                && dob.isNotEmpty()

    }

    fun checkPassword(password: String, confirmPassword: String): Boolean {
        return password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword
    }


}