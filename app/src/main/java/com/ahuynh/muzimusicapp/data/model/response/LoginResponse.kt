package com.ahuynh.muzimusicapp.data.model.response

data class Response(
    val id: Long,
    val email: String,
    val username: String,
    val enabled: Boolean,
    val avatar: String,
    val deviceToken : String,
    val role : List<String>

    )

data class LoginResponse(
    val message: String,
    val data: Response
)