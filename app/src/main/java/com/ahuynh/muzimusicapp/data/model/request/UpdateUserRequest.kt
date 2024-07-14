package com.ahuynh.muzimusicapp.data.model.request

data class UpdateUserRequest(
    val id : Long,
    val email: String,
    val username: String
)
