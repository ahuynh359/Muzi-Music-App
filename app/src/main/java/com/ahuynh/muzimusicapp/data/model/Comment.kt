package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val id: Long,
    val content: String,
    val user: User,
    val  time : String,
    val createdAt: String,
    val updatedAt: String

) : Parcelable