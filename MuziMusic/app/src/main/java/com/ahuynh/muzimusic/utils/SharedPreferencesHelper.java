package com.ahuynh.muzimusic.utils;

import static com.ahuynh.muzimusic.utils.Contants.PACKAGE_NAME;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PACKAGE_NAME, Context.MODE_PRIVATE
        );
        return sharedPreferences.edit();
    }

    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(
                PACKAGE_NAME, Context.MODE_PRIVATE
        );
    }
}
