package com.ahuynh.muzimusicapp.data_api.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginRequest(val userNameOrEmail: String, val password : String) : Parcelable