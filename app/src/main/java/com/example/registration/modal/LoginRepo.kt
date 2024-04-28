package com.example.registration.modal

import android.content.Context
import com.example.registration.constants.PasswordHash
import com.example.registration.data.localDB.LocalDB
import com.example.registration.data.localDB.RegistrationDao
import com.example.registration.data.localDB.RegistrationEntity
import javax.inject.Inject

class LoginRepo @Inject constructor(mContext: Context) {
    private val registrationDao: RegistrationDao = LocalDB.getIntance(context = mContext).registrationDao

    private fun getDBData(): RegistrationEntity {
        return registrationDao.getSignupDetails()
    }

    fun validateCredentials(email:String,password:String):Boolean{
        val passwordHash = PasswordHash.generateHash(password = password)
        val userDetails = getDBData()

        return email== userDetails.primaryEmail && passwordHash == userDetails.password

    }

}