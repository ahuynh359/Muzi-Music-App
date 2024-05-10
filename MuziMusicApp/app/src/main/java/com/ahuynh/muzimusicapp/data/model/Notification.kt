package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification(
    val id : String ?= null,
    val count: Int? = null,
    val data: ArrayList<String>? = arrayListOf()

) : Parcelable