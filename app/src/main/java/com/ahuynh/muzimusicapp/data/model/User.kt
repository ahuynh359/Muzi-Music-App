package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class User(
    val id: Long,
    val email: String,
    val username: String,
    val avatar: String,
    val locked : Boolean,
    val roles: List<Role>,
) : Parcelable