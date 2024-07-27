package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val id: Long,
    val content: String,
    val user: User,
    val  time : String,
    val commentParentId : Long ?= null,
    val replies : List<Comment>,
    val createdAt: String,
    val updatedAt: String

) : Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comment

        if (id != other.id) return false
        if (user != other.user) return false
        if (content != other.content) return false
        if (time != other.time) return false
        if (replies != other.replies) return false
        if(commentParentId != other.commentParentId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + replies.hashCode()
        result = 31 * result + commentParentId.hashCode()
        return result
    }
}