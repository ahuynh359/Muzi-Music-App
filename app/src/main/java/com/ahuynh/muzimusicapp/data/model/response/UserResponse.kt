package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Role
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import com.google.firebase.installations.R
import java.time.Instant

data class UserResponse(
    val id: Long,
    val email: String,
    val username: String,
    val avatar: String,
    val locked: Boolean,
    val deviceToken: String,
    val role: Role,
    val createdAt: String,
    val updatedAt: String


) {
    fun toUser(): User {
        return User(
            id = this.id,
            email = this.email,
            username = this.username,
            avatar = this.avatar,
            locked = this.locked,
            deviceToken = this.deviceToken,
            role = this.role,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}

data class UserResponseData(
    val message: String,
    val data: UserResponse
)

data class UserResponseDataList(
    val message: String,
    val data: List<UserResponse>
)


fun List<UserResponse>.toListUser(): List<User> {
    return map { it.toUser() }
}
