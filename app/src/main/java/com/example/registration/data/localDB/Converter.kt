package com.example.registration.data.localDB

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.io.ByteArrayOutputStream

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun listToJsonString(value: List<String>): String? =
        if (value.isEmpty()) null else gson.toJson(value)


    @TypeConverter
    fun jsonStringToList(value: String?): List<String>? {
        return if (value.isNullOrEmpty()) null else gson.fromJson(value, Array<String>::class.java)
            .toList()
    }

    @TypeConverter
    fun convertBitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        return if (bitmap != null) {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.toByteArray()
        } else {
            null
        }
    }

    @TypeConverter
    fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
        return if (byteArray != null) {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } else {
            null
        }

    }
}
