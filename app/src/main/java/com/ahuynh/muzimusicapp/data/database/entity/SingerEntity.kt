package com.ahuynh.muzimusicapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Type

@Entity
data class SingerEntity(
    @PrimaryKey
    val singerId: Long,
    val name: String,
    val avatar: String
) {
    fun toSinger(): Singer {
        return Singer(
            id = this.singerId,
            name = this.name,
            avatar = this.avatar
        )
    }
}
fun List<SingerEntity>.toListSinger(): List<Singer> {
    return map{it.toSinger()}
}
