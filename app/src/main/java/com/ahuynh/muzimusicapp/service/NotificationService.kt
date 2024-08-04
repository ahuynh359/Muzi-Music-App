package com.ahuynh.muzimusicapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ahuynh.muzimusicapp.MuziMusicApplication
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.ui.component.notification_common.NotificationCommonActivity
import com.ahuynh.muzimusicapp.ui.component.user.UserActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("vinh", "onMessageReceived")

        var pref: SharedPreferences =
            applicationContext.getSharedPreferences(
                SharePreferencesHelper.APP_SHARE_KEY,
                Context.MODE_PRIVATE
            )
        val isLoggedIn = pref.getBoolean(SharePreferencesHelper.IS_LOGGED_IN, false)

        if(isLoggedIn){
            val title = message.notification?.title ?: message.data["title"]
            val content = message.notification?.body ?: message.data["content"]
            val type = message.data["type"]
            val referenceId = message.data["referenceId"]

            sendNotification(title, content, type,referenceId)
            Log.d("ABC", "Action: $title $content $type $referenceId")
        }
    }


    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")


    }


    private fun sendNotification(title: String?, content: String?, type: String?,referenceId: String?) {
        val intent = Intent(this, NotificationCommonActivity::class.java).apply {
            putExtra(Constants.TYPE, type)
            putExtra(Constants.REFERENCE_ID, referenceId?.toLong())
        }


        val pendingIntent: PendingIntent? =
            TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                )
            }

        val icon = BitmapFactory.decodeResource(
            this.resources,
            R.drawable.ic_headphone
        )

        val notification = NotificationCompat.Builder(this, MuziMusicApplication.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_headphone)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_headphone)
            .setContentIntent(pendingIntent)
            .setLargeIcon(icon)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(icon)
            )
            .setAutoCancel(true)
            .build()

        val notificationManager: NotificationManager? =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.notify(2, notification)
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }


}








