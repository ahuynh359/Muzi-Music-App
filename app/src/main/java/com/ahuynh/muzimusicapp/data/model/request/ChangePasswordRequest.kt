package com.ahuynh.muzimusicapp.data.model.request

data class ChangePasswordRequest(
    val id : Long,
    val oldPassword : String,
    val newPassword : String,
    val confirmPassword : String
)