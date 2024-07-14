package com.ahuynh.muzimusicapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahuynh.muzimusicapp.data.model.Type

@Entity
data class TypeEntity(
    @PrimaryKey
    val typeId: Long,
    val name: String,
    val avatar: String,
    val createdAt: String, val updatedAt: String
) {
    fun toType(): Type {
        return Type(
            id = this.typeId,
            name = this.name,
            avatar = this.avatar,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt

        )
    }
}
fun List<TypeEntity>.toListType(): List<Type> {
    return map{it.toType()}
}