package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Playlist

data class PlaylistResponse(
    val id: Long,
    val name: String,
     val userId: Long,
    val avatar : String


) {
    fun toPlaylistResponse(): Playlist {
        return Playlist(
            id = this.id,
            name = this.name,
            userId = this.userId,
            avatar = this.avatar
        )
    }
}

data class PlaylistResponseData(
    val message: String,
    val data: PlaylistResponse
)

data class ListPlaylistResponse(
    val data: List<PlaylistResponse>
)

fun List<PlaylistResponse>.toPlaylistResponse(): List<Playlist> {
    return map { it.toPlaylistResponse() }
}

