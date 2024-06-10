package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Album
import java.time.Instant

data class AlbumResponse(
    val id: Long,
    val name: String,
    val avatar: String,


) {
    fun toAlbum(): Album {
        return Album(
            id = this.id,
            name = this.name,
            avatar = this.avatar,

        )
    }
}

data class ListAlbumResponse(
    val message: String,
    val data: List<AlbumResponse>
)

fun List<AlbumResponse>.toListAlbum(): List<Album> {
    return map { it.toAlbum() }
}
