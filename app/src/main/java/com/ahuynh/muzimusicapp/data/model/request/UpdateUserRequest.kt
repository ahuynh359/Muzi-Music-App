package com.ahuynh.muzimusicapp.data.model.request

data class UpdateUserRequest(
    val email: String,
    val password: String,
    val username: String
)
