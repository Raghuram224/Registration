package com.example.registration.modal

import android.content.Context
import android.util.Log
import com.example.registration.constants.PasswordHash
import com.example.registration.constants.constantModals.UserDetails
import com.example.registration.data.localDB.LocalDB
import com.example.registration.data.localDB.RegistrationDao
import com.example.registration.data.localDB.RegistrationEntity
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

class LocalDBRepo @Inject constructor(mContext: Context) {
    private val registrationDao: RegistrationDao =
        LocalDB.getInstance(context = mContext).registrationDao

    private lateinit var currentUserEmail: String

    lateinit var userDetailsFlow: Flow<RegistrationEntity>
    lateinit var currentUserDetails: RegistrationEntity
    private var currentUserSID: Int = 0

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

    fun updateUserDetails(userDetails: UserDetails) {
       try {
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

                   )
           )
       }catch (e:Exception){
           Log.i("DB exception",e.toString())
       }
    }


    fun authenticateEmail(email: String): Boolean {
        var isValid = false
        try {
            currentUserDetails = registrationDao.getUserDetails(email = email)!!
            if (email == currentUserDetails.primaryEmail) {
                currentUserEmail = email
                currentUserSID = currentUserDetails.sid
                userDetailsFlow = registrationDao.getSignupDetailsFlow(rowId = currentUserSID)
                isValid = true
            } else {
                isValid = false
            }
        } catch (e: Exception) {
            isValid = false
        }
        return isValid


    }

    fun authenticatePassword(password: String): Boolean {
        return password == currentUserDetails.password
    }


}