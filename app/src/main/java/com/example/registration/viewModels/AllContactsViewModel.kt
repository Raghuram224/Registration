package com.example.registration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.constants.constantModals.ContactBasicDetails
import com.example.registration.modal.LocalDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllContactsViewModel @Inject constructor(
    private val localDBRepo: LocalDBRepo
) : ViewModel() {



    fun getAllContacts(): List<ContactBasicDetails> {
        return localDBRepo.getAllContacts()
    }

}