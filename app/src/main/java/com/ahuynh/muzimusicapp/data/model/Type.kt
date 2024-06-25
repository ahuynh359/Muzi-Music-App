package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Type(val id: Long, val name: String, val avatar : String) :
    Parcelable