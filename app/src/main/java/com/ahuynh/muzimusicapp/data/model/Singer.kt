package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Singer(
    val id: Long,
    val name: String,
    val avatar: String,
) :
    Parcelable