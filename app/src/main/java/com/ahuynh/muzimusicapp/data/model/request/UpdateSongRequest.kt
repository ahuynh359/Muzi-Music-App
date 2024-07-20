package com.ahuynh.muzimusicapp.data.model.request

data class UpdateSongRequest(
    val id : Long,
    val name : String,
    val lyrics : String,
    val albumId : Long,
    val singerId : Set<Long>,
    val typeId : Set<Long>
)
