package com.example.registration.viewModels

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.registration.modal.LocalDBRepo
import com.example.registration.ui.theme.Blue
import com.example.registration.ui.theme.DarkGreen
import com.example.registration.ui.theme.RedBG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val localDBRepo: LocalDBRepo,
) : ViewModel() {

    private var _userDetails = MutableStateFlow( entityToDataClassConverter())
    private val listOfColor = listOf(DarkGreen, RedBG, Blue)
    var isUiColorSelected = false
    private var tempColor = listOfColor.random()
    val uiColor: Color =updateUIColor()

    val userDetails = _userDetails.asStateFlow()
//    init {
//        userDetails =
//    }

    private fun entityToDataClassConverter():PersonalInformation {
        val userDetails = localDBRepo.userDetails
        return PersonalInformation(
            firstName = userDetails.firstName,
            lastName = userDetails.lastName,
            age = userDetails.age,
            dob = userDetails.dob,
            address = userDetails.address,
            primaryEmail = userDetails.primaryEmail,
            primaryPhone = userDetails.primaryPhone,
            otherPhones = convertStringToList(userDetails.otherPhones),
            otherEmails = convertStringToList(userDetails.otherEmails)?.toMutableList(),
            profileImage = userDetails.profileImage,
            website = userDetails.website

        )

    }

    private fun convertStringToList(text: String?): List<String>? {
        return if (text != null) {
            text.split(",")
        } else null
    }
    fun updateUIData(){
        println("Contact "+localDBRepo.userDetails)
        localDBRepo.updateDBData()
        _userDetails = MutableStateFlow( entityToDataClassConverter())
    }

    private fun updateUIColor():Color{
        return if (!isUiColorSelected){
            isUiColorSelected =true
            val uiColor =listOfColor.random()
            tempColor = uiColor
            uiColor
        }else{
            tempColor
        }
    }


}

data class PersonalInformation(
    val firstName: String,
    val lastName: String,
    val age: String,
    val address: String,
    val dob: String,
    val primaryEmail: String,
    val primaryPhone: String,
    val otherEmails: List<String>?,
    val otherPhones: List<String>?,
    val profileImage:Bitmap?,
    val website:String,

    )