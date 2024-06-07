package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Role

data class RoleResponse(
    val id: Long,
    val name: String,


    ) {
    fun toRole(): Role {
        return Role(
            id = this.id,
            name = this.name,
        )
    }
}

data class ListRoleResponse(
    val data: List<AlbumResponse>
)

fun List<RoleResponse>.toRoleList(): List<Role> {
    return map { it.toRole() }
}


