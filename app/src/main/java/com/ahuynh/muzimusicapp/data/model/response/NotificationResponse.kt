package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Notification

data class NotificationResponse(
    val id: Long,
    val title: String,
    val content: String,
    val status: String,
    val type: String,
    val time: String,
    val songId: Long,
    val commentId: Long,
    val user: UserResponse,
) {
    fun toNotification(): Notification {
        return Notification(
            id = id,
            title = title,
            content = content,
            status = status,
            type = type,
            time = time,
            songId = songId,
            commentId = commentId,
            user = user.toUser()

        )
    }
}

data class NotificationResponseData(
    val message: String,
    val data: NotificationResponse
)

data class NotificationResponseDataList(
    val message: String,
    val data: List<NotificationResponse>
)

data class NotificationResponseCount(
    val message: String,
    val data: Count
)

data class Count(
    val count: Int
)

fun List<NotificationResponse>.toListNotification(): List<Notification> {
    return map { it.toNotification() }
}
