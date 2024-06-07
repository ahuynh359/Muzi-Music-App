package com.ahuynh.muzimusicapp.data.model.request

data class SongRequest(
    val name : String,
    val avatar : String ,
    val lyrics : String ,
    val singer : String,
    val albumId : Long
)
