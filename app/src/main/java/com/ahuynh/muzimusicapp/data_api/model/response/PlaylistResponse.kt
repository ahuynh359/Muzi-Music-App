package com.ahuynh.muzimusicapp.data_api.model.response

import com.ahuynh.muzimusicapp.data_api.model.Album

data class AlbumResponse(
    val id: Long,
    val name: String,
    val description: String,
    val avatar: String,
    val createdAt: String


) {
    fun toAlbum(): Album {
        return Album(
            id = this.id, name = this.name, description = this.description, avatar = this.avatar

        )
    }
}

data class ListAlbumResponse(
    val success: Boolean,
    val message: String,
    val data: List<AlbumResponse>
)

fun List<AlbumResponse>.toListAlbum(): List<Album> {
    return map { it.toAlbum() }
}
