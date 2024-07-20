package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.AddCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.EditCommentRequest
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseData
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseWithTotalCommentList
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.data.service.remote.CommentRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CommentRepository @Inject constructor(
    private val commentRemoteService: CommentRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRemoteService() {

    suspend fun getAllCommentsOfSong(id: Long): Response<CommentResponseWithTotalCommentList> {
        return withContext(dispatcher) {
            commentRemoteService.getAllCommentsOfSong(id)
        }
    }

    suspend fun getAllComments(sortName: SortName): List<Comment> {
        return withContext(dispatcher) {
            commentRemoteService.getAllComments(sortName)
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
