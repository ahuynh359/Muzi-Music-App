package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PushNotification(
    val data: NotificationData,
    val to: String
): Parcelable