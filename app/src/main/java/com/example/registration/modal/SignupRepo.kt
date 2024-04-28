package com.example.registration.modal

import android.content.Context
import com.example.registration.constants.PasswordHash
import com.example.registration.data.localDB.LocalDB
import com.example.registration.data.localDB.RegistrationDao
import com.example.registration.data.localDB.RegistrationEntity
import com.example.registration.view.signupScreen.UserDetails
import javax.inject.Inject

class SignupRepo @Inject constructor( mContext: Context) {

    private val registrationDao:RegistrationDao = LocalDB.getIntance(context = mContext).registrationDao

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