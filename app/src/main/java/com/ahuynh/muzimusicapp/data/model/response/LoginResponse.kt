package com.ahuynh.muzimusicapp.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class LoginResponse(
    val id: Long,
    val username: String,
    val email: String,
    val jwt: String,

    )