package com.ahuynh.muzimusicapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahuynh.muzimusicapp.data.model.Album

@Entity
data class AlbumEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val avatar: String,
    val createdAt: String,
    val updatedAt: String
) {
    fun toAlbum(): Album {
        return Album(
            id = this.id,
            name = this.name,
            avatar = this.avatar,
            createdAt = this.createdAt,
            updatedAt = this.createdAt
        )
    }
}