package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class Playlist(
    val id: Long,
    val name: String,
    val userId: Long,
    val avatar : String
) :
    Parcelable