package com.example.registration.modal

import android.content.Context
import com.example.registration.constants.PasswordHash
import com.example.registration.data.localDB.LocalDB
import com.example.registration.data.localDB.RegistrationDao
import com.example.registration.data.localDB.RegistrationEntity
import com.example.registration.view.signupScreen.UserDetails
import javax.inject.Inject

class LocalDBRepo @Inject constructor(mContext: Context) {
    private val registrationDao: RegistrationDao =
        LocalDB.getIntance(context = mContext).registrationDao

    private  var userDetails:RegistrationEntity
    init {
        userDetails = getDBData()
    }



    private fun getDBData(): RegistrationEntity {
        return registrationDao.getSignupDetails()
    }


    fun validateEmail(email: String): Boolean {

        return if (userDetails!=null) email == userDetails.primaryEmail else false
    }

    fun validatePassword(password: String): Boolean {
        val passwordHash = PasswordHash.generateHash(password = password)
        return if (userDetails!=null) passwordHash == userDetails.password else false
    }

    fun insetIntoDb(userDetails: UserDetails){
        registrationDao.insertSignupDetails(
            RegistrationEntity(
                firstName = userDetails.firstName,
                lastName = userDetails.lastName,
                age = userDetails.age,
                dob = userDetails.dob,
                address = userDetails.address,
                primaryEmail = userDetails.primaryEmail,
                otherEmails = userDetails.otherEmails,
                primaryPhone = userDetails.primaryPhone,
                otherPhones = userDetails.otherPhones,
                password = PasswordHash.generateHash(password = userDetails.password)
            )
        )
    }

    fun clearData(){
        registrationDao.clearData()
    }

}