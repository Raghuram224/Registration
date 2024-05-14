package com.example.registration.modal

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.registration.constants.PasswordHash
import com.example.registration.constants.constantModals.ContactBasicDetails
import com.example.registration.constants.constantModals.UserDetails
import com.example.registration.data.localDB.LocalDB
import com.example.registration.data.localDB.RegistrationDao
import com.example.registration.data.localDB.RegistrationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDBRepo @Inject constructor(mContext: Context) {


    private val registrationDao: RegistrationDao =
        LocalDB.getInstance(context = mContext).registrationDao


    suspend fun insetIntoDb(userDetails: UserDetails) {

        Log.i("result repo",userDetails.otherEmails.toString()+"phone"+userDetails.otherPhones.toString())

        registrationDao.insertSignupDetails(
            RegistrationEntity(
                firstName = userDetails.firstName.trim(),
                lastName = userDetails.lastName.trim(),
                age = userDetails.age,
                address = userDetails.address.trim(),
                dob = userDetails.dob,
                primaryEmail = userDetails.primaryEmail.trim(),
                primaryPhone = userDetails.primaryPhone.trim(),
                otherEmails = userDetails.otherEmails,
                otherPhones = userDetails.otherPhones,
                website = userDetails.website.trim(),
                password = PasswordHash.generateHash(password = userDetails.password.trim()),
                profileImage = if (userDetails.profileImage != null) userDetails.profileImage else null,
                isAdmin = false

            )
        )
    }

    fun updateUserDetails(userDetails: UserDetails, currentUserSID: Int) {

        registrationDao.updateUserDetails(
            RegistrationEntity(
                sid = currentUserSID,
                firstName = userDetails.firstName.trim(),
                lastName = userDetails.lastName.trim(),
                age = userDetails.age,
                address = userDetails.address.trim(),
                dob = userDetails.dob,
                primaryEmail = userDetails.primaryEmail.trim(),
                primaryPhone = userDetails.primaryPhone.trim(),
                otherEmails = userDetails.otherEmails,
                otherPhones = userDetails.otherPhones,
                website = userDetails.website.trim(),
                password = userDetails.password.trim(),
                profileImage = if (userDetails.profileImage != null) userDetails.profileImage else null,
                isAdmin = false
            )
        )

    }

    fun checkUserExist(email: String, password: String): String? {
        val userDetails = registrationDao.checkUserDetails(email = email)
        if (userDetails != null) {
            if (email == userDetails.primaryEmail && password == userDetails.password) return userDetails.sid.toString()
        }
        return null
    }

    fun userDetailsFlow(rowId: Int): Flow<RegistrationEntity> {
        return registrationDao.getUserDetailsFlow(rowId = rowId)
    }

    fun getUserDetails(userId: Int): RegistrationEntity {
        return registrationDao.getUserDetails(userId = userId)
    }

    fun getAllContacts(): List<ContactBasicDetails> {
        val allContacts = mutableListOf<ContactBasicDetails>()
        registrationDao.getAllContactsDetails().forEach { user ->
            allContacts.add(
                ContactBasicDetails(
                    fName = user.firstName,
                    lName = user.lastName,
                    userId = user.sid,
                    profileImage = user.profileImage
                )
            )

        }

        return allContacts

    }

    fun checkIsAdmin(userId: Int): Boolean {
        return getUserDetails(userId = userId).isAdmin
    }

    fun checkEmailIdAvailable(email: String):Boolean{
        val dbEmail = registrationDao.getEmailFromDB(email = email)
        return dbEmail == null
    }

    fun getProfileImage(userId: Int): Bitmap? {
        val userDetails =getUserDetails(userId = userId)
        return  userDetails.profileImage
    }


}

