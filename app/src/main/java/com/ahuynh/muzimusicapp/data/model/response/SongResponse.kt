package com.ahuynh.muzimusicapp.data.model.response

import android.os.Build
import androidx.annotation.RequiresApi
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Song
import com.google.gson.Gson
import java.time.Instant


data class SongResponse(
    val id: Long,
    val name: String,
    val avatar: String,
    val file: String,
    val lyrics: String,
    val album: AlbumResponse,
    val singers: List<SingerResponse>,
    val types: List<TypeResponse>,
    val createdAt : String,
    val updatedAt : String



) {
    fun toSong(): Song {
        return Song(
            id = this.id,
            name = this.name,
            avatar = this.avatar,
            file = this.file,
            lyrics = this.lyrics,
            album = this.album.toAlbum(),
            singers = this.singers.toListSinger(),
            types = this.types.toListType(),
            createdAt = this.createdAt,
            updatedAt = this.updatedAt

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


