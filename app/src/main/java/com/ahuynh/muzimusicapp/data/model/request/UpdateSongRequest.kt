package com.ahuynh.muzimusicapp.data.model.request

data class UpdateSongRequest(
    val name : String,
    val albumId : Long,
    val lyrics : String,
    val singer : String
)
