package com.ahuynh.muzimusicapp.data.model.response

data class Response(
    val id: Long,
    val email: String,
    val username: String,
    val enabled: Boolean,

    )

data class LoginResponse(
    val message: String,
    val data: Response
)