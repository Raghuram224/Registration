package com.example.registration.data.localDB

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registration_table")
data class RegistrationEntity(
    @PrimaryKey(autoGenerate = true)
    val sid: Int = 0,

    @ColumnInfo(name = "firstName")
    val firstName: String,

    @ColumnInfo(name = "lastName")
    val lastName: String,

    @ColumnInfo(name = "age")
    val age: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "dob")
    val dob: String,

    @ColumnInfo(name = "primaryEmail")
    val primaryEmail: String,

    @ColumnInfo(name = "primaryPhone")
    val primaryPhone: String,

    @ColumnInfo(name = "website")
    val website: String,

    @ColumnInfo(name = "otherPhones")
    val otherPhones: String?,

    @ColumnInfo(name = "otherEmails")
    val otherEmails: String?,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "profileImage")
    val profileImage:Bitmap?,

    @ColumnInfo(name = "isAdmin")
    val isAdmin:Boolean

)