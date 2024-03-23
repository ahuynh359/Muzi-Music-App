package com.ahuynh.muzimusicapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.ahuynh.muzimusicapp.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.ahuynh.muzimusicapp.utils.VersionHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MuziMusicApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    //Create an notification channel starting from API 26
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