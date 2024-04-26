package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val name: String ? = null,
    val id: String? = null,
    val file: String ? = null,
    val image: String ? = null,
    val lyrics: String? = null,
    val singer: String? = null,
    val listen: Int? = 0,
    var love : Boolean = false,
    val listens: Map<String, Int> = mapOf()
) : Parcelable

