package com.ahuynh.muzimusic.utils;

import android.os.Build;

public class VersionHelper {
    public static boolean isVersionQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }
}
