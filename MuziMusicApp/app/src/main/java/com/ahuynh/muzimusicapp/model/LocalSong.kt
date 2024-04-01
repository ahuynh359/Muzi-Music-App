package com.ahuynh.muzimusicapp.model

import android.net.Uri

data class LocalSong(
    val uri: Uri,
    val displayName : String,
    val id : Long,
    val artist : String,
    val data : String,
    val duration : Int,
    val title : String
)