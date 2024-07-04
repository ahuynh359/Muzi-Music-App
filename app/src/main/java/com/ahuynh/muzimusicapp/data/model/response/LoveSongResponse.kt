package com.ahuynh.muzimusicapp.data.model.response

import androidx.core.text.util.LocalePreferences.FirstDayOfWeek.Days

data class LoveSongResponse(
    val message: String,
    val data: LoveSong

)

data class LoveSong(
    val total: String,
    val songs: List<SongResponse>
)

