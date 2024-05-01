package com.example.registration.data.localDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RegistrationEntity::class], version = 6)
@TypeConverters(Converter::class)
abstract class LocalDB:RoomDatabase() {
    abstract val registrationDao:RegistrationDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDB?=null

        fun getInstance(context: Context): LocalDB {
            var tempInstance = INSTANCE
            if (tempInstance==null){
                tempInstance= Room.databaseBuilder(
                    context.applicationContext,
                    LocalDB::class.java,
                    "Registration_Database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }

            INSTANCE = tempInstance

            return tempInstance
        }
    }
}