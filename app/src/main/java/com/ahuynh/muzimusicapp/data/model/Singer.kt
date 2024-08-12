package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Singer(
    val id: Long,
    val name: String,
    val avatar: String,
    val description: String ?= null,
    val createdAt: String,
    val updatedAt: String
) :
    Parcelable