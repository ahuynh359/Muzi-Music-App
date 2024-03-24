package com.ahuynh.muzimusicapp.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.service.MusicService
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Utils {

    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
        // If parcelableList is null, return null; otherwise, return a new ArrayList with the same elements
    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

    inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
        SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
    }

    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
        SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }

    fun Int.toTimeFormat(): String {
        val hour = this / 3600
        val minute = (this % 3600) / 60
        val second = (this % 3600) % 60

        return if (hour == 0) {
            if (minute < 10)
                String.format("%d:%02d", minute, second)
            else String.format("%02d:02d", minute, second)
        } else {
            String.format("%d:%02d:%02d", hour, minute, second)
        }
    }

    fun checkSinglePermissionAny(
        activity: Activity,
        permissionName: String,
        permissionCode: Int
    ): Boolean {
        if (ContextCompat.checkSelfPermission(
                activity,
                permissionName
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permissionName),
                permissionCode
            )
        } else {
            return true
        }
        return false
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



    fun sendMusic(
        context: Context,
        action: Int,
        song: Song? = null,
        songList: ArrayList<Song> = arrayListOf()
    ) {

        val bundle = Bundle().apply {
            putParcelable(Constants.SONG, song)
            putParcelableArrayList(Constants.SONG_LIST, songList)
        }

        val intent = Intent(context , MusicService::class.java).apply {
            putExtra(Constants.ACTION,action)
            putExtra(Constants.DATA, bundle)
        }

        if (VersionHelper.isO()) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }

    }
}