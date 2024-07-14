package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val id: Long,
    val name: String,
    val avatar: String,
    val file: String,
    val lyrics: String,
    val album: Album,
    val singers: List<Singer>,
    val types: List<Type>, val createdAt: String,
    val updatedAt: String


) : Parcelable {
    fun toSongEntity(): SongEntity {
        val gson = Gson()

        val albumJson = gson.toJson(this.album)
        val singersJson = gson.toJson(this.singers)
        val typesJson = gson.toJson(this.types)

        return SongEntity(
            songId = this.id,
            name = this.name,
            avatar = this.avatar,
            file = this.file,
            lyrics = this.lyrics,
            album = albumJson,
            singers = singersJson,
            types = typesJson,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            listenAt = System.currentTimeMillis()
        )
    }
}