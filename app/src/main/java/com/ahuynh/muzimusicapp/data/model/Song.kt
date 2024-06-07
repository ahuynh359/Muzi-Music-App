package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val id: Long,
    val name: String,
    val avatar: String,
    val file: String,
    val lyrics: String,
    val album: Album,
    val listen: Long,
    val singer: String,
    val types : List<Type>

) : Parcelable