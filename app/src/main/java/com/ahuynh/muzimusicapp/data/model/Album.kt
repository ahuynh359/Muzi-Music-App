package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class Album(
    val id: Long,
    val name: String,
    val description: String,
    val avatar: String,
    val createdAt: Instant
) :
    Parcelable