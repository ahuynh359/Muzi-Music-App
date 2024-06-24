package com.ahuynh.muzimusicapp.data.model.response

data class Response(
    val id: Long,
    val username: String,
    val email: String,
    val jwt: String
)

data class LoginResponse(
    val message : String,
    val data : Response
)

