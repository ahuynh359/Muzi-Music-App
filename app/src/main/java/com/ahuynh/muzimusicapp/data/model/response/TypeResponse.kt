package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Type

data class TypeResponse(
    val id: Long,
    val name: String,


    ) {
    fun toType(): Type {
        return Type(
            id = this.id, name = this.name

        )
    }
}

data class ListTypeResponse(
    val message: String,
    val data: List<TypeResponse>
)

fun List<TypeResponse>.toListType(): List<Type> {
    return map { it.toType() }
}
