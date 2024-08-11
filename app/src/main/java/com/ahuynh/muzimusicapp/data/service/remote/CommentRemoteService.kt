package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.CommentAPI
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.request.AddCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.EditCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.ReplyCommentRequest
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseData
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseWithTotalCommentList
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.toCommentList
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.NetworkResult
import javax.inject.Inject

class CommentRemoteService @Inject constructor(
    private val commentAPI: CommentAPI
) : BaseRemoteService() {
    suspend fun getAllCommentsOfSong(id: Long): NetworkResult<CommentResponseWithTotalCommentList> {
        return callApi { commentAPI.getAllCommentsOfSong(id) }
    }

    suspend fun getCommentById(id: Long): Comment? {
        val result = callApi { commentAPI.getCommentById(id) }
        return if (result is NetworkResult.Success) {
            result.data.data.toComment()
        } else {
            null
        }
    }

    suspend fun createComment(commentRequest: AddCommentRequest): NetworkResult<CommentResponseData> {
        return callApi { commentAPI.createComment(commentRequest) }
    }

    suspend fun editComment(editCommentRequest: EditCommentRequest): NetworkResult<CommentResponseData> {
        return callApi { commentAPI.editComment(editCommentRequest) }
    }

    suspend fun deleteComment(id: Long): NetworkResult<MessageResponse> {
        return callApi { commentAPI.deleteComment(id) }
    }

    suspend fun getAllComments(sort: SortName): List<Comment> {
        val result = callApi { commentAPI.getAllComments(sort) }
        return if (result is NetworkResult.Success) {
            result.data.data.toCommentList()
        } else {
            arrayListOf()
        }
    }

    suspend fun replyComment(replyCommentRequest: ReplyCommentRequest): NetworkResult<CommentResponseData> {
        return callApi { commentAPI.replyComment(replyCommentRequest) }
    }

}



