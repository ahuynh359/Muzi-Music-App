package com.ahuynh.muzimusic.utils;


import static com.ahuynh.muzimusic.utils.Contants.PERMISSION_REQUEST_CODE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {

    public static final String[] PERMISSIONS = {
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.POST_NOTIFICATIONS
    };

    public static boolean hasPermissions(Context context) {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    public static void requestPermissions(Activity context) {
        ActivityCompat.requestPermissions(context, PERMISSIONS, PERMISSION_REQUEST_CODE);
    }



}
