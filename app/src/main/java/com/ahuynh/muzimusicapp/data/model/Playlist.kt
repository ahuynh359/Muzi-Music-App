package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class Playlist(
    val id: Long,
    val name: String,
    val user: User,
    val songs: List<Song>,
    val createdAt: String
) :
    Parcelable