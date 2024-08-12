package com.ahuynh.muzimusicapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahuynh.muzimusicapp.data.model.Singer

@Entity
data class SingerEntity(
    @PrimaryKey
    val singerId: Long,
    val name: String,
    val avatar: String,
    val description : String ?= null,
    val createdAt : String,
    val updatedAt : String
) {
    fun toSinger(): Singer {
        return Singer(
            id = this.singerId,
            name = this.name,
            avatar = this.avatar,
            description = this.description,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}
fun List<SingerEntity>.toListSinger(): List<Singer> {
    return map{it.toSinger()}
}
