package com.example.registration.data.localDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistrationDao {
    @Insert
    suspend fun insertSignupDetails(registrationEntity: RegistrationEntity)

    @Query("select * from registration_table where sid = :rowId")
    fun getUserDetailsFlow(rowId: Int): Flow<RegistrationEntity>


//    @Query("select * from registration_table  order by sid limit 1")
//    suspend fun getSignupDetails(): RegistrationEntity
//
//    @Query("delete  from registration_table")
//    suspend fun clearData()

    @Query("select * from registration_table where primaryEmail =:email")
    fun checkUserDetails(email: String): RegistrationEntity?

    @Query("SELECT (SELECT COUNT(*) FROM registration_table) == 0")
    suspend fun isDBEmpty(): Boolean


    @Update
    fun updateUserDetails(registrationEntity: RegistrationEntity)

    @Query("select * from registration_table where sid =:userId")
    fun getUserDetails(userId:Int): RegistrationEntity
}