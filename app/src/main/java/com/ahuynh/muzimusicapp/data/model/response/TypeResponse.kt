package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Type

data class TypeResponse(
    val id: Long,
    val name: String,
    val description: String,


    ) {
    fun toType(): Type {
        return Type(
            id = this.id, name = this.name, description = this.description

        )
    }
}

data class ListTypeResponse(
    val success: Boolean,
    val message: String,
    val data: List<TypeResponse>
)

fun List<TypeResponse>.toListType(): List<Type> {
    return map { it.toType() }
}
