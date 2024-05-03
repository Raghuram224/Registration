package com.example.registration.modal

import android.content.Context
import com.example.registration.constants.PasswordHash
import com.example.registration.constants.constantModals.UserDetails
import com.example.registration.data.localDB.LocalDB
import com.example.registration.data.localDB.RegistrationDao
import com.example.registration.data.localDB.RegistrationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDBRepo @Inject constructor(mContext: Context) {
    private val registrationDao: RegistrationDao =
        LocalDB.getInstance(context = mContext).registrationDao

    /* private lateinit var currentUserEmail: String

     lateinit var userDetailsFlow: Flow<RegistrationEntity>
     lateinit var currentUserDetails: RegistrationEntity
     private var currentUserSID: Int = 0*/

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
                profileImage = if (userDetails.profileImage != null) userDetails.profileImage else null

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
//                profileImage = if (userDetails.profileImage != null) userDetails.profileImage else null,
                profileImage = userDetails.profileImage
                )
        )

    }

    fun checkUserExist(email: String, password: String): Int {
        val userDetails = registrationDao.checkUserDetails(email = email)
        if (userDetails != null) {
            if (email == userDetails.primaryEmail && password == userDetails.password) return userDetails.sid
        }
        return -1
    }

    fun userDetailsFlow(rowId: Int): Flow<RegistrationEntity> {
        return registrationDao.getUserDetailsFlow(rowId = rowId)
    }

    fun getUserDetails(userId:Int):RegistrationEntity{
        return registrationDao.getUserDetails(userId = userId)
    }



}

