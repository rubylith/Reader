package com.fyl.reader.utils

import android.app.AppOpsManager
import android.content.Context

object PermissionUtils {
    private const val MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage"

    fun checkStoragePermission(context: Context): Boolean {
        val appOps = context.getSystemService(AppOpsManager::class.java)
        val mode = appOps.unsafeCheckOpNoThrow(
            MANAGE_EXTERNAL_STORAGE_PERMISSION,
            context.applicationInfo.uid,
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }
}
