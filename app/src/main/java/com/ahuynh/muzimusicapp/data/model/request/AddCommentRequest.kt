package com.ahuynh.muzimusicapp.data.model.request


data class AddCommentRequest(
    val songId : Long,
    val content : String
)