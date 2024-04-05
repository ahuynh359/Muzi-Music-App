package com.ahuynh.muzimusicapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lyric(val startTime: Int, val text: String) : Parcelable