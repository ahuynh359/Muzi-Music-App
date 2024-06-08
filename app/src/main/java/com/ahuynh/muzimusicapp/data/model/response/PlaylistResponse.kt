package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Playlist
import com.squareup.moshi.Json

data class PlaylistResponse(
    val id: Long,
    val name: String,
     val users: UserResponse,
    val songs: List<SongResponse>,
    val createdAt: String


) {
    fun toPlaylistResponse(): Playlist {
        return Playlist(
            id = this.id,
            name = this.name,
            users = this.users.toUser(),
            songs = this.songs.toListSong(),
            createdAt = this.createdAt,
        )
    }
}

data class ListPlaylistResponse(
    val data: List<PlaylistResponse>
)

fun List<PlaylistResponse>.toPlaylistResponse(): List<Playlist> {
    return map { it.toPlaylistResponse() }
}

