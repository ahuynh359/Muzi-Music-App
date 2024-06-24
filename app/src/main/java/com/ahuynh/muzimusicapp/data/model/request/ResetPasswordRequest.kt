package com.ahuynh.muzimusicapp.data.model.request

data class ResetPasswordRequest(
    val otp: String,
    val newPassword: String,
    val confirmPassword: String
)