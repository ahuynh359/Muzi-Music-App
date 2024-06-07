package com.ahuynh.muzimusicapp.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class SignUpResponse(
    val id: Long,
    val username: String,
    val email: String
)