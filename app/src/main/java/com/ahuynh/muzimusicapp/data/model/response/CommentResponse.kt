package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Comment

data class CommentResponse(
    val id: Long,
    val content: String,
    val user: UserResponse,
    val parentCommentId: Long ?= null,
    val createdAt: String,
    val updatedAt: String

) {
    fun toComment(): Comment {
        return Comment(
            id = this.id,
            content = this.content,
            user = this.user.toUser(),
            parentCommentId = this.parentCommentId ,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}

data class CommentResponseWithTotalComment(
    val comments: List<CommentResponse>,
    val totalComments: Int,
)

data class CommentResponseWithTotalCommentList(
    val message: String,
    val data: CommentResponseWithTotalComment
)

data class CommentResponseDataList(
    val message: String,
    val data: List<CommentResponse>
)

data class CommentResponseData(
    val message: String,
    val data: CommentResponse
)

fun List<CommentResponse>.toCommentList(): List<Comment> {
    return map { it.toComment() }
}
