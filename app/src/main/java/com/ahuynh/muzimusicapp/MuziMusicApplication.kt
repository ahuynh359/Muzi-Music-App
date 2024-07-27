package com.ahuynh.muzimusicapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.ahuynh.muzimusicapp.utils.helper.VersionHelper
import dagger.hilt.android.HiltAndroidApp
import androidx.work.Configuration
import androidx.work.WorkManager
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import java.util.Locale

@HiltAndroidApp
class MuziMusicApplication :  LocalizationApplication(){

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "Muzi Channel"
    }

    override fun getDefaultLanguage(context: Context): Locale {
        return Locale.ENGLISH
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }


    private fun createNotificationChannel() {
        if (VersionHelper.isO()) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                getString(R.string.notifications),
                NotificationManager.IMPORTANCE_LOW
            )
            channel.description = getString(R.string.descriptionText)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }



}