package com.ahuynh.muzimusicapp.utils.helper

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharePreferencesHelper @Inject constructor(context: Context) {
    companion object {
        const val SHUFFLE = "shuffle"
        const val REPEAT = "repeat"
        const val APP_SHARE_KEY = "com.ahuynh.muzimusicapp"
    }

    private var pref = context.getSharedPreferences(APP_SHARE_KEY, Context.MODE_PRIVATE)
    private var editor = pref.edit()

    fun isShuffle(): Boolean {
        return pref.getBoolean(SHUFFLE, false)
    }

    fun setShuffle(isShuffle: Boolean) {
        editor.putBoolean(SHUFFLE, isShuffle)
        editor.apply()
    }

    fun isRepeat(): Boolean {
        return pref.getBoolean(REPEAT, false)
    }

    fun setRepeat(isRepeat: Boolean) {
        editor.putBoolean(REPEAT, isRepeat)
        editor.apply()
    }

}