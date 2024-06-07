package com.ahuynh.muzimusicapp.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class PlaylistRequest(val name: String, val userId : Long)