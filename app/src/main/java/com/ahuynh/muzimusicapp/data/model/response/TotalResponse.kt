package com.ahuynh.muzimusicapp.data.model.response

data class Total (
    val totalSong: String,
    val totalAlbum: String,
    val totalUser: String,
    val totalType: String,
    val totalSinger: String
)
data class TotalResponse(
    val message : String,
    val data : Total
)