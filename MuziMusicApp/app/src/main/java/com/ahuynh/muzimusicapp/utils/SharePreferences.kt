package com.ahuynh.muzimusicapp.utils

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharePreferences @Inject constructor(private val context: Context) {
    companion object {
        const val SHUFFLE = "shuffle"
        const val APP_SHARE_KEY = "com.ahuynh.muzimusicapp"
    }

    private var pref = context.getSharedPreferences(APP_SHARE_KEY, Context.MODE_PRIVATE)
    private var editor = pref.edit()

    fun isShuffle(): Boolean {
        return pref.getBoolean(SHUFFLE, false)
    }

    fun setShuffle(isShuffle: Boolean) {
        editor.putBoolean(SHUFFLE, isShuffle)
        editor.commit()
    }

}