package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Playlist

data class PlaylistResponse(
    val id: Long,
    val name: String,
    val avatar : String


) {
    fun toPlaylistResponse(): Playlist {
        return Playlist(
            id = this.id,
            name = this.name,
            avatar = this.avatar
        )
    }
}

data class PlaylistResponseData(
    val message: String,
    val data: List<PlaylistResponse>
)

data class PlaylistResponseJson(
    val message: String,
    val data: PlaylistResponse
)


fun List<PlaylistResponse>.toPlaylistResponse(): List<Playlist> {
    return map { it.toPlaylistResponse() }
}

