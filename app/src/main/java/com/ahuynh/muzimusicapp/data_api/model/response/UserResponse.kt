package com.ahuynh.muzimusicapp.data_api.model.response

import com.ahuynh.muzimusicapp.data_api.model.User

data class UserResponse(
    val id: Long,
    val email: String,
    val username: String,
    val avatar: String,
    val enabled: Boolean,
    val role: List<RoleResponse>,


    ) {
    fun toUser(): User {
        return User(
            id = this.id,
            email = this.email,
            username = this.username,
            avatar = this.avatar,
            enabled = this.enabled,
            role = this.role.toRole()
        )
    }
}

data class ListUserResponse(
    val success: Boolean,
    val message: String,
    val data: List<UserResponse>
)

fun List<UserResponse>.toListUser(): List<User> {
    return map { it.toUser() }
}
