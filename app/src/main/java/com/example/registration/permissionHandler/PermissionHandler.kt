package com.example.registration.permissionHandler

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import javax.inject.Inject

class PermissionHandler @Inject constructor(
    val mContext: Context
) {
    //permission
    val cameraPermissions = arrayOf(
        Manifest.permission.CAMERA
    )
    val phonePermissions = arrayOf(
        Manifest.permission.CALL_PHONE
    )
    fun hasRequiredPermission(permissions:Array<String>): Boolean {

        return permissions.all {
            ContextCompat.checkSelfPermission(
                mContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}