package com.ahuynh.muzimusicapp.utils

import android.os.Build

object VersionHelper {

    // Android 10
    fun isQ() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    // Android 8
    fun isO() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    //Android 13
    fun isTiramisu() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}