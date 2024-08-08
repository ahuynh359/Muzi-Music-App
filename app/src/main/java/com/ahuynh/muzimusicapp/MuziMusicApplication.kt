package com.ahuynh.muzimusicapp


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.ahuynh.muzimusicapp.ui.component.error.ErrorActivity
import com.ahuynh.muzimusicapp.utils.helper.VersionHelper
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale


@HiltAndroidApp
class MuziMusicApplication : LocalizationApplication() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "Muzi Channel"
    }

    override fun getDefaultLanguage(context: Context): Locale {
        return Locale.ENGLISH
    }

    override fun onCreate() {

        super.onCreate()

        createNotificationChannel()
        customActivityOnCrash()
    }

    private fun customActivityOnCrash() {
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
            .enabled(true) //default: true
            .showErrorDetails(false) //default: true
            .showRestartButton(false) //default: true
            .logErrorOnRestart(false) //default: true
            .trackActivities(true) //default: false
            .minTimeBetweenCrashesMs(2000) //default: 3000
            .errorDrawable(R.drawable.ic_spotify_a) //default: bug image
            .restartActivity(ErrorActivity::class.java) //default: null (your app's launch activity)
            .errorActivity(ErrorActivity::class.java) //default: null (default error activity)
            .apply()
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