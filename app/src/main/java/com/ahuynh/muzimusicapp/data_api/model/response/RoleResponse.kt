package com.ahuynh.muzimusicapp.data_api.model.response

import com.ahuynh.muzimusicapp.data_api.model.Album
import com.ahuynh.muzimusicapp.data_api.model.Role
import com.google.firebase.appcheck.interop.R

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

fun List<RoleResponse>.toRole(): List<Role> {
    return map { it.toRole() }
}


