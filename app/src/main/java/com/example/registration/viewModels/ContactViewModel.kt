package com.example.registration.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.registration.navigation.IS_ADMIN_KEY
import com.example.registration.navigation.USER_ID_KEY
import com.example.registration.constants.constantModals.PersonalInformation
import com.example.registration.modal.LocalDBRepo
import com.example.registration.ui.theme.Blue
import com.example.registration.ui.theme.DarkGreen
import com.example.registration.ui.theme.RedBG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    localDBRepo: LocalDBRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var currentUserId = savedStateHandle.get<String>(USER_ID_KEY)
    var isAdmin = savedStateHandle.get<Boolean>(IS_ADMIN_KEY)
    private val _uiColor = listOf(DarkGreen, RedBG, Blue).random()
    val uiColor = _uiColor

    @OptIn(ExperimentalCoroutinesApi::class)
    val contactDetails =
        localDBRepo.userDetailsFlow(rowId = (currentUserId ?: "-1").toInt()).mapLatest {
            PersonalInformation(
                dob = it.dob,
                age = it.age,
                lastName = it.lastName,
                firstName = it.firstName,
                address = it.address,
                primaryPhone = it.primaryPhone,
                primaryEmail = it.primaryEmail,
                otherPhones =  it.otherPhones,
                otherEmails =  it.otherEmails,
                website = it.website,
                profileImage = it.profileImage
            )
        }

    override fun onCleared() {
        super.onCleared()
        println("contact view model cleared")
    }


}



