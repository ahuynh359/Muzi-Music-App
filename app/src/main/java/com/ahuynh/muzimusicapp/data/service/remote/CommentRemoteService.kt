package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.CommentAPI
import com.ahuynh.muzimusicapp.data.model.request.AddCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.EditCommentRequest
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseData
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import javax.inject.Inject

class CommentRemoteService @Inject constructor(
    private val commentAPI: CommentAPI
) : BaseRemoteService() {
    suspend fun getAllCommentsOfSong(id: Long): com.ahuynh.muzimusicapp.utils.Response<CommentResponseDataList> {
        return callApi { commentAPI.getAllCommentsOfSong(id) }
    }


    suspend fun createComment(commentRequest: AddCommentRequest): com.ahuynh.muzimusicapp.utils.Response<CommentResponseData> {
        return callApi { commentAPI.createComment(commentRequest) }
    }

    suspend fun editComment(editCommentRequest: EditCommentRequest): com.ahuynh.muzimusicapp.utils.Response<CommentResponseData> {
        return callApi { commentAPI.editComment(editCommentRequest) }
    }

    suspend fun deleteComment(id: Long): com.ahuynh.muzimusicapp.utils.Response<MessageResponse> {
        return callApi { commentAPI.deleteComment(id) }
    }
}



