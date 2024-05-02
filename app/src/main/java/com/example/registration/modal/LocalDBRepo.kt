package com.example.registration.modal

import android.content.Context
import com.example.registration.constants.PasswordHash
import com.example.registration.data.localDB.LocalDB
import com.example.registration.data.localDB.RegistrationDao
import com.example.registration.data.localDB.RegistrationEntity
import com.example.registration.view.signupScreen.UserDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDBRepo @Inject constructor(mContext: Context) {
    private val registrationDao: RegistrationDao =
        LocalDB.getInstance(context = mContext).registrationDao

    var userDetailsFlow: Flow<RegistrationEntity> = registrationDao.getSignupDetailsFlow()
    var userDetails = getDBData()


//        fun validateEmail(email: String): Boolean {
//// val passwordHash = PasswordHash.generateHash(password = password)
//        val userDetails = userDetails.collectLatest {  }
//        return if (userDetails != null) email == userDetails. else false
//    }
//
//    fun validatePassword(password: String): Boolean {
//        val passwordHash = PasswordHash.generateHash(password = password)
////        return if (userDetails != null) passwordHash == userDetails.password else false
//        return if (userDetails != null) password == userDetails.password else false
//    }
    private fun getDBData(): RegistrationEntity {
        return registrationDao.getSignupDetails()
    }

    fun insetIntoDb(userDetails: UserDetails) {
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
//                password = PasswordHash.generateHash(password = userDetails.password),
                password = userDetails.password,
                profileImage = if (userDetails.profileImage != null) userDetails.profileImage else null

            )
        )
    }

    fun clearData() {
        registrationDao.clearData()
    }

    fun isLocalDbEmpty(): Boolean {
        return registrationDao.isDBEmpty()
    }

    fun updateDbData() {
        userDetails = getDBData()
    }


}