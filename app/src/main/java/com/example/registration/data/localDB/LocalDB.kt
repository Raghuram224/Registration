package com.example.registration.data.localDB

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RegistrationEntity::class], version = 8)
@TypeConverters(Converter::class)
abstract class LocalDB:RoomDatabase() {
    abstract val registrationDao:RegistrationDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDB?=null

        fun getInstance(context: Context): LocalDB {
            synchronized(this){
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
}


//fun getInstance(context: Context): LocalDB {
//    synchronized(this){
//        return LocalDB.instance ?:Room.databaseBuilder(
//            context = context.applicationContext,
//            klass = LocalDB::class.java,
//            name = "Registration_Database"
//        ).allowMainThreadQueries().fallbackToDestructiveMigration().build().also {
//            LocalDB.instance = it
//        }
//    }
//}