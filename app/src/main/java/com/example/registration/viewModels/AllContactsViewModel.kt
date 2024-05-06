package com.example.registration.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.registration.constants.constantModals.ContactBasicDetails
import com.example.registration.modal.LocalDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllContactsViewModel @Inject constructor(
    private val localDBRepo: LocalDBRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    fun getAllContacts(): List<ContactBasicDetails> {
        return localDBRepo.getAllContacts()
    }

}