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




