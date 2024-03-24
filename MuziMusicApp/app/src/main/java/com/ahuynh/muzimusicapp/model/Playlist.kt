package com.ahuynh.muzimusicapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Playlist(
    val name: String? = null,
    val id: String? = null,
    val image: String? = null,
    val songs: ArrayList<String>? = arrayListOf(),
) : Parcelable
