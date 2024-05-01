package com.example.registration.data.localDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface RegistrationDao {
    @Insert
    fun insertSignupDetails(registrationEntity: RegistrationEntity)

    @Query("select * from registration_table  order by sid limit 1")
    fun getSignupDetails(): RegistrationEntity

    @Query("delete  from registration_table")
    fun clearData()

    @Query("SELECT (SELECT COUNT(*) FROM registration_table) == 0")
    fun isDBEmpty(): Boolean
}