package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Playlist
import java.time.Instant

data class PlaylistResponse(
    val id: Long,
    val name: String,
    val user: UserResponse,
    val song: List<SongResponse>,
    val createdAt: String


) {
    fun toPlaylistResponse(): Playlist {
        return Playlist(
            id = this.id,
            name = this.name,
            user = this.user.toUser(),
            songs = this.song.toListSong(),
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

