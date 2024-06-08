package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Playlist

data class SearchResponse(
    val success : Boolean,
    val message : String,
    val data : SearchJson
)

data class SearchJson(
    val songs : List<SongResponse>,
    val albums : List<AlbumResponse>,
    val users : List<UserResponse>
)