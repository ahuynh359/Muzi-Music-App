package com.ahuynh.muzimusicapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.ahuynh.muzimusicapp.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.ahuynh.muzimusicapp.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.ahuynh.muzimusicapp.utils.VersionHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MuziMusicApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    //Create an notification channel start from API 26
    private fun createNotificationChannel() {
        if (VersionHelper.isO()) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_NAME,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE


            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}