package com.ahuynh.muzimusicapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val name: String ? = null,
    val id: String? = null,
    val file: String ? = null,
    val image: String ? = null,
    val lyrics: String ? = null,
    val singer: String ? = null,
    val playlistId: Int? = null
) : Parcelable

