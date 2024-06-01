package com.ahuynh.muzimusicapp.data_api.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpRequest(val email: String, val password: String, val username: String) : Parcelable