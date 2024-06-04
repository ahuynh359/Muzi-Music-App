package com.ahuynh.muzimusicapp.data_api.model.response

import com.ahuynh.muzimusicapp.data_api.model.Song


data class SongResponse(
    val id: String,
    val name: String,
    val avatar: String,
    val file: String,
    val lyrics: String,
    val album: AlbumResponse,
    val listen: Long


) {
    fun toSong(): Song {
        return Song(
            id = this.id,
            name = this.name,
            avatar = this.avatar,
            file = this.file,
            lyrics = this.lyrics,
            album = this.album.toAlbum(),
            listen = this.listen,
        )
    }
}

data class ListSongResponse(
    val success: Boolean,
    val message: String,
    val data: List<SongResponse>
)

fun List<SongResponse>.toListSong(): List<Song> {
    return map { it.toSong() }
}
