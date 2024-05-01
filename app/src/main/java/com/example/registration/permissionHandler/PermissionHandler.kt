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
    private val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA
    )
    fun hasRequiredPermission(): Boolean {

        return PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                mContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}