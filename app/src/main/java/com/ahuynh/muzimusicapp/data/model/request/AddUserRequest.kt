package com.ahuynh.muzimusicapp.data.model.request

data class AddUserRequest(
    val email: String,
    val password: String,
    val username: String
)