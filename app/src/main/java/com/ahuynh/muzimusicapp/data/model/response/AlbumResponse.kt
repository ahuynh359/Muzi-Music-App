package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Album
import java.time.Instant

data class AlbumResponse(
    val id: Long,
    val name: String,
    val description: String,
    val avatar: String,
    val createdAt: Instant


) {
    fun toAlbum(): Album {
        return Album(
            id = this.id,
            name = this.name,
            description = this.description,
            avatar = this.avatar,
            createdAt = this.createdAt

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
