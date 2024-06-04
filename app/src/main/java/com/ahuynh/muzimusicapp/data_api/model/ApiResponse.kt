package com.ahuynh.muzimusicapp.data_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ApiResponse(val success : Boolean , val message : String, val data : Any)