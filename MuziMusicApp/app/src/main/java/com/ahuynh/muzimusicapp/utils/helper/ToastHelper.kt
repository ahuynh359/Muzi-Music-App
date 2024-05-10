package com.ahuynh.muzimusicapp.utils.helper

import android.app.Activity
import android.content.Context
import android.widget.Toast

object ToastHelper {
    fun makeToastPermissionGranted(activity : Activity){
        Toast.makeText(activity,"Permission Granted",Toast.LENGTH_SHORT).show()

    }

    fun makeErrorToast(context : Context, s : String){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show()
    }
}