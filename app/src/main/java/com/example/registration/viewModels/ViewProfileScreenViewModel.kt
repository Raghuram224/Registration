package com.example.registration.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.registration.modal.LocalDBRepo
import com.example.registration.navigation.USER_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewProfileScreenViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localDBRepo: LocalDBRepo
) : ViewModel() {
    private var currentUserId = savedStateHandle.get<String>(USER_ID_KEY)

    val userProfileImage = getProfileImage()
    private fun getProfileImage(): Bitmap? {
        return currentUserId?.let { localDBRepo.getProfileImage(userId = it.toInt()) }
    }
}