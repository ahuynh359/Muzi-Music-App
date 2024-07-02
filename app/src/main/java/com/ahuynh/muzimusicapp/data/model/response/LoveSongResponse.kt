package com.ahuynh.muzimusicapp.data.model.response

import androidx.core.text.util.LocalePreferences.FirstDayOfWeek.Days

data class LoveSongResponse(
    val message: String,
    val data: Love

)

data class Love(
    val total: String,
    val songs: List<SongResponse>
)

