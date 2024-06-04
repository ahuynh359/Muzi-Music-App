package com.ahuynh.muzimusicapp.data_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val id: String,
    val name: String,
    val avatar: String,
    val file: String,
    val lyrics: String,
    val album: Album,
    val listen: Long
) : Parcelable