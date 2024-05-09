package com.example.registration.modal

import android.content.Context
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
        registrationDao.insertSignupDetails(
            RegistrationEntity(
                firstName = userDetails.firstName,
                lastName = userDetails.lastName,
                age = userDetails.age,
                address = userDetails.address,
                dob = userDetails.dob,
                primaryEmail = userDetails.primaryEmail,
                primaryPhone = userDetails.primaryPhone,
                otherEmails = userDetails.otherEmails,
                otherPhones = userDetails.otherPhones,
                website = userDetails.website,
                password = PasswordHash.generateHash(password = userDetails.password),
                profileImage = if (userDetails.profileImage != null) userDetails.profileImage else null,
                isAdmin = false

            )
        )
    }

    fun updateUserDetails(userDetails: UserDetails, currentUserSID: Int) {

        registrationDao.updateUserDetails(
            RegistrationEntity(
                sid = currentUserSID,
                firstName = userDetails.firstName,
                lastName = userDetails.lastName,
                age = userDetails.age,
                address = userDetails.address,
                dob = userDetails.dob,
                primaryEmail = userDetails.primaryEmail,
                primaryPhone = userDetails.primaryPhone,
                otherEmails = userDetails.otherEmails,
                otherPhones = userDetails.otherPhones,
                website = userDetails.website,
                password = userDetails.password,
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
        println("values"+registrationDao.getAllContactsDetails().toString())
        return allContacts

    }

    fun checkIsAdmin(userId: Int): Boolean {
        return getUserDetails(userId = userId).isAdmin
    }

    fun checkEmailIsAlreadyUsed(email: String):Boolean{
        val dbEmail = registrationDao.getEmailFromDB(email = email)
        Log.i("check email",dbEmail.toString())
        return dbEmail == null
    }


}

