package com.ahuynh.muzimusicapp.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class SignUpRequest(val email: String, val password: String, val username: String)