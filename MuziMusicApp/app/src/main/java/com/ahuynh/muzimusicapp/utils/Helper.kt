package com.ahuynh.muzimusicapp.utils

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.service.MusicService

object Helper {

    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
        // If parcelableList is null, return null; otherwise, return a new ArrayList with the same elements
    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

    inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
        SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
    }

    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
        SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }

    fun convertMillisToMinutesAndSeconds(durationInMillis: Long): String {
        val totalSeconds = durationInMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%d", minutes, seconds)
    }




    fun sendMusic(
        context: Context,
        action: Int,
        song: Song? = null,
        songList: ArrayList<Song> = arrayListOf()
    ) {

        val bundle = Bundle().apply {
            putParcelable(Constants.SONG, song)
            putParcelableArrayList(Constants.SONG_LIST, songList)
        }

        val intent = Intent(context , MusicService::class.java).apply {
            putExtra(Constants.ACTION,action)
            putExtra(Constants.DATA, bundle)
        }

        if (VersionHelper.isO()) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }

    }
}