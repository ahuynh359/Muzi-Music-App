package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Role
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import java.time.Instant

data class UserResponse(
    val id: Long,
    val email: String,
    val username: String,
    val avatar: String,
    val enabled: Boolean,
    val role: List<String>,



    ) {
    fun toUser(): User {
        return User(
            id = this.id,
            email = this.email,
            username = this.username,
            avatar = this.avatar,
            enabled = this.enabled,
            role = this.role,
        )
    }
}

data class UserResponseData(
    val message : String,
    val data : UserResponse
)

data class ListUserResponse(
    val success: Boolean,
    val message: String,
    val data: List<UserResponse>
)

fun List<UserResponse>.toListUser(): List<User> {
    return map { it.toUser() }
}
