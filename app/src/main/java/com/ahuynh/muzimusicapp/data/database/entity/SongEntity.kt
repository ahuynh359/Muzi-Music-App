package com.ahuynh.muzimusicapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
data class SongEntity(
    @PrimaryKey
    val songId: Long,
    val name: String,
    val avatar: String,
    val file: String,
    val lyrics: String,
    val album: String,
    val singers: String,
    val types: String,
    val createdAt : String,
    val updatedAt : String,
    val listenAt : Long,
){
    fun toSong(): Song {
        val gson = Gson()

        val albumType = object : TypeToken<Album>() {}.type
        val album: Album = gson.fromJson(this.album, albumType)

        val singersType = object : TypeToken<List<Singer>>() {}.type
        val singers: List<Singer> = gson.fromJson(this.singers, singersType)

        val typesType = object : TypeToken<List<Type>>() {}.type
        val types: List<Type> = gson.fromJson(this.types, typesType)

        return Song(
            id = this.songId,
            name = this.name,
            avatar = this.avatar,
            file = this.file,
            lyrics = this.lyrics,
            album = album,
            singers = singers,
            types = types,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}
fun List<SongResponse>.toListSong(): List<Song> {
    return map { it.toSong() }
}