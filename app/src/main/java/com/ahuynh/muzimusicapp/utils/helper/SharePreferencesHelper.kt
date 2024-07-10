package com.ahuynh.muzimusicapp.utils.helper

import android.content.Context
import org.bouncycastle.cms.RecipientId.password
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharePreferencesHelper @Inject constructor(context: Context) {
    companion object {
        const val SHUFFLE = "shuffle"
        const val REPEAT = "repeat"
        const val UNREAD_NOTI = "unread_noti"
        const val IS_LOGGED_IN = "is_logged_in"
        const val TOKEN = "token"
        const val APP_SHARE_KEY = "com.ahuynh.muzimusicapp"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val ID = "id"
        const val IS_ADMIN_OR_USER = "is_admin_or_user"
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

    fun getUnreadNoti(): Int {
        return pref.getInt(UNREAD_NOTI, 0)
    }

    fun setUnreadNoti(unread: Int) {
        editor.putInt(UNREAD_NOTI, unread)
        editor.apply()
    }


    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGGED_IN, false)
    }

    fun saveLoggedIn(email: String, password: String) {
        editor.putString(EMAIL, email)
        editor.putString(PASSWORD, password)
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.apply()

    }

    fun getEmail(): String {
        return pref.getString(EMAIL, "") ?: ""

    }

    fun saveToken(token: String) {
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String {
        return pref.getString(TOKEN, null) ?: ""
    }

    fun saveId(id: Long) {
        editor.putLong(ID, id)
        editor.apply()
    }

    fun logout() {
        editor.putString(EMAIL, "")
        editor.putString(PASSWORD, "")
        editor.putBoolean(IS_LOGGED_IN, false)
        editor.apply()
    }


    fun getId(): Long {
        return pref.getLong(ID, 1) ?: 1
    }

    //Admin is true - user is false
    fun setIsAdminOrUser(b: Boolean) {
        editor.putBoolean(IS_ADMIN_OR_USER, b)
        editor.apply()
    }

    fun getAdminOrUser(): Boolean {
        return pref.getBoolean(IS_ADMIN_OR_USER, true)
    }

}