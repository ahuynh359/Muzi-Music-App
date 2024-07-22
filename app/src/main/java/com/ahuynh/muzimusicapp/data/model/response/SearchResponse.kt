package com.ahuynh.muzimusicapp.data.model.response



data class SearchResponse(
    val songs : List<SongResponse>,
    val albums : List<AlbumResponse>,
    val singers : List<SingerResponse>
)

data class ListSearchResponse(
    val message : String,
    val data : SearchResponse
)