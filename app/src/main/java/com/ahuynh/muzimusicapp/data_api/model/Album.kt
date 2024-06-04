package com.ahuynh.muzimusicapp.data_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(val id: Long, val name: String, val description: String, val avatar: String) :
    Parcelable