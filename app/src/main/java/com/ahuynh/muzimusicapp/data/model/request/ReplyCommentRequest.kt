package com.ahuynh.muzimusicapp.data.model.request

data class ReplyCommentRequest (
    val songId : Long,
    val parentId : Long,
    val content : String
)