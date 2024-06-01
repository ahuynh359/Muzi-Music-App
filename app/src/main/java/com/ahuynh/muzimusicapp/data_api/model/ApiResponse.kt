package com.ahuynh.muzimusicapp.data_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiResponse(val status : String , val message : String) : Parcelable