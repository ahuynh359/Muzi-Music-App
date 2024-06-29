package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Song


data class SongResponse(
    val id: Long,
    val name: String,
    val avatar: String,
    val file: String,
    val lyrics: String,
    val album: AlbumResponse,



) {
    fun toSong(): Song {
        return Song(
            id = this.id,
            name = this.name,
            avatar = this.avatar,
            file = this.file,
            lyrics = this.lyrics,
            album = this.album.toAlbum()

        )
    }
}

data class SongResponseData(
    val message : String,
    val data: List<SongResponse>
)

fun List<SongResponse>.toListSong(): List<Song> {
    return map { it.toSong() }
}
