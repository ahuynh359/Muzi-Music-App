package com.ahuynh.muzimusicapp.utils

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.LocaleList
import android.os.Parcelable
import android.util.Patterns
import android.util.TypedValue
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.palette.graphics.Palette
import com.ahuynh.muzimusicapp.data.model.Lyric
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.service.BroadcastService
import com.ahuynh.muzimusicapp.service.BroadcastService.Companion.DURATION
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.utils.helper.PermissionHelper.warningPermissionDialog
import com.ahuynh.muzimusicapp.utils.helper.VersionHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.internal.ContextUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object Utils {

    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

    inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
        }

    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
        }

    fun Int.toTimeFormat(): String {
        val hour = this / 3600
        val minute = (this % 3600) / 60
        val second = (this % 3600) % 60

        return if (hour == 0) {
            if (minute < 10)
                String.format("%d:%02d", minute, second)
            else String.format("%02d:02d", minute, second)
        } else {
            String.format("%d:%02d:%02d", hour, minute, second)
        }
    }

    fun String.convertStringToLyric(): Lyric {
        val closeBracketIndex = indexOf(']')
        val time = substring(1, closeBracketIndex)
        val towDot = time.indexOf(':')
        val dot = time.indexOf('.')
        val minute = time.substring(1, towDot).toInt()
        val second = time.substring(towDot + 1, dot).toInt()
        val millis = time.substring(dot + 1).toInt()
        val timeMillis = minute * 60 * 1000 + second * 1000 + millis * 10

        return Lyric(timeMillis, substring(closeBracketIndex + 1).trim())
    }


    fun convertDpToPixel(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }


    fun sendNewMusic(
        context: Context,
        action: Int,
        song: Song? = null,
        songList: ArrayList<Song> = arrayListOf()
    ) {

        val bundle = Bundle().apply {
            putParcelable(Constants.SONG, song)
            putParcelableArrayList(Constants.SONG_LIST, songList)
        }

        val intent = Intent(context, MusicService::class.java).apply {
            putExtra(Constants.ACTION, action)
            putExtra(Constants.DATA, bundle)
        }

        startService(context, intent)
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

        val intent = Intent(context, MusicService::class.java).apply {
            putExtra(Constants.ACTION, action)
            putExtra(Constants.DATA, bundle)
        }

        startService(context, intent)
    }

    fun startService(context: Context, intent: Intent) {
        if (VersionHelper.isO()) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }

    }

    fun startSleepService(
        context: Context,
        time: Long
    ) {

        val bundle = Bundle().apply {
            putLong(DURATION, time)
        }

        val intent = Intent(context, BroadcastService::class.java).apply {
            putExtra(Constants.DATA, bundle)
        }

        startService(context, intent)
    }


    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun formatTime(millisUntilFinished: Long): String {
        val totalSeconds = millisUntilFinished / 1000
        val hours = totalSeconds / 3600
        val min = (totalSeconds % 3600) / 60
        val sec = totalSeconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, min, sec)
    }

    fun ImageView.loadImage(url: String) {
        Glide
            .with(this.context)
            .load(url)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }



}