package com.ahuynh.muzimusicapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.ahuynh.muzimusicapp.utils.NetworkConnectivityHelper
import com.ahuynh.muzimusicapp.utils.VersionHelper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MuziMusicApplication : Application(){

    private lateinit var snackbar: Snackbar
    private val networkConnectivityObserver: NetworkConnectivityHelper by lazy {
        NetworkConnectivityHelper(this)
    }
    companion object {
        const val NOTIFICATION_CHANNEL_ID = "Muzi Channel"
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
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = getString(R.string.descriptionText)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}