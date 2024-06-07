package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Song


data class SongResponse(
    val id: Long,
    val name: String,
    val avatar: String,
    val file: String,
    val lyrics: String,
    val album: AlbumResponse,
    val listen: Long,
    val singer: String,
    val types: List<TypeResponse>


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
            singer = this.singer,
            types = this.types.toListType()
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
