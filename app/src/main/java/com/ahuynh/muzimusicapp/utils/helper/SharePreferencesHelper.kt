package com.ahuynh.muzimusicapp.utils.helper

import android.content.Context
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
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
        const val SORT_USER = "sort_user"
        const val SORT_TYPE = "sort_type"
        const val SORT_SONG = "sort_song"
        const val SORT_ALBUM = "sort_album"
        const val SORT_SINGER = "sort_singer"
        const val SORT_COMMENT = "sort_comment"
        const val LANGUAGE = "en"

    }

    private var pref = context.getSharedPreferences(APP_SHARE_KEY, Context.MODE_PRIVATE)
    private var editor = pref.edit()

    fun getLanguage(): String {
        return pref.getString(LANGUAGE, "en") ?: "en"
    }

    fun setLanguage(language: String) {
        editor.putString(LANGUAGE, language)
        editor.apply()
    }

    fun isShuffle(): Boolean {
        return pref.getBoolean(SHUFFLE, false)
    }

    fun setShuffle(isShuffle: Boolean) {
        editor.putBoolean(SHUFFLE, isShuffle)
        editor.apply()
    }

    fun isSortSinger(): SortName {
        val sortString = pref.getString(SORT_SINGER, SortName.NEW.name) ?: SortName.NEW.name
        return try {
            SortName.valueOf(sortString)
        } catch (e: IllegalArgumentException) {
            SortName.NEW
        }
    }

    fun setSortSinger(sortName: SortName) {
        editor.putString(SORT_SINGER, sortName.name)
        editor.apply()
    }

    fun isSortComment(): SortName {
        val sortString = pref.getString(SORT_COMMENT, SortName.NEW.name) ?: SortName.NEW.name
        return try {
            SortName.valueOf(sortString)
        } catch (e: IllegalArgumentException) {
            SortName.NEW
        }
    }

    fun setSortComment(sortName: SortName) {
        editor.putString(SORT_COMMENT, sortName.name)
        editor.apply()
    }


    fun isSortUser(): SortName {
        val sortString = pref.getString(SORT_USER, SortName.NEW.name) ?: SortName.NEW.name
        return try {
            SortName.valueOf(sortString)
        } catch (e: IllegalArgumentException) {
            SortName.NEW
        }
    }

    fun setSortUser(sortName: SortName) {
        editor.putString(SORT_USER, sortName.name)
        editor.apply()
    }

    fun isSortAlbum(): SortName {
        val sortString = pref.getString(SORT_ALBUM, SortName.NEW.name) ?: SortName.NEW.name
        return try {
            SortName.valueOf(sortString)
        } catch (e: IllegalArgumentException) {
            SortName.NEW
        }
    }

    fun setSortAlbum(sortName: SortName) {
        editor.putString(SORT_ALBUM, sortName.name)
        editor.apply()
    }

    fun isSortSong(): SortName {
        val sortString = pref.getString(SORT_SONG, SortName.NEW.name) ?: SortName.NEW.name
        return try {
            SortName.valueOf(sortString)
        } catch (e: IllegalArgumentException) {
            SortName.NEW
        }
    }

    fun setSortSong(sortName: SortName) {
        editor.putString(SORT_SONG, sortName.name)
        editor.apply()
    }


    fun isSortType(): SortName {
        val sortString = pref.getString(SORT_TYPE, SortName.NEW.name) ?: SortName.NEW.name
        return try {
            SortName.valueOf(sortString)
        } catch (e: IllegalArgumentException) {
            SortName.NEW
        }
    }

    fun setSortType(sortName: SortName) {
        editor.putString(SORT_TYPE, sortName.name)
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