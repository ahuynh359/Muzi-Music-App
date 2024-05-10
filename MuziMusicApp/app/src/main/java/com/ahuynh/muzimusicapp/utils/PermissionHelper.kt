package com.ahuynh.muzimusicapp.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object PermissionHelper {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val multiplePermissionNameList = mutableListOf(
        android.Manifest.permission.POST_NOTIFICATIONS,
        android.Manifest.permission.RECORD_AUDIO
    )

    val listPermissionNeeded = arrayListOf<String>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkMultiplePermission(
        activity: Activity,
        permissionCode: Int
    ): Boolean {
        for (permission in multiplePermissionNameList) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                listPermissionNeeded.add(permission)
            }
        }
        if (listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                listPermissionNeeded.toTypedArray(),
                permissionCode
            )
            return false
        }
        return true
    }

    fun appSettingOpen(context: Context) {
        val settingIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        settingIntent.data = Uri.parse("package:${context.packageName}")
        context.startActivity(settingIntent)
    }

    fun warningPermissionDialog(context: Context, listener: DialogInterface.OnClickListener) {
        MaterialAlertDialogBuilder(context)
            .setMessage("All Permissions Are Required For This App")
            .setCancelable(false)
            .setPositiveButton("Ok", listener)
            .create()
            .show()
    }
}