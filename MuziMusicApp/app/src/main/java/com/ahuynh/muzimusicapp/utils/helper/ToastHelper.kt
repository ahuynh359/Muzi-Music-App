package com.ahuynh.muzimusicapp.utils.helper

import android.app.Activity
import android.widget.Toast

object ToastHelper {
    fun makeToastPermissionGranted(activity : Activity){
        Toast.makeText(activity,"Permission Granted",Toast.LENGTH_SHORT).show()

    }
}