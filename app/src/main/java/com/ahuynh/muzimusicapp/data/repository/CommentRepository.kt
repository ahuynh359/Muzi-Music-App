package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.request.AddCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.EditCommentRequest
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseData
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.data.service.remote.CommentRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CommentRepository @Inject constructor(
    private val commentRemoteService: CommentRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRemoteService() {

    suspend fun getAllCommentsOfSong(id: Long): Response<CommentResponseDataList> {
        return withContext(dispatcher) {
            commentRemoteService.getAllCommentsOfSong(id)
        }
    }


    suspend fun createComment(commentRequest: AddCommentRequest): Response<CommentResponseData> {
        return withContext(dispatcher) {
            commentRemoteService.createComment(commentRequest)
        }
    }

    suspend fun editComment(editCommentRequest: EditCommentRequest): Response<CommentResponseData> {
        return withContext(dispatcher) {
            commentRemoteService.editComment(editCommentRequest)
        }
    }

    suspend fun deleteComment(id: Long): Response<MessageResponse> {
        return withContext(dispatcher) {
            commentRemoteService.deleteComment(id)
        }
    }
}
