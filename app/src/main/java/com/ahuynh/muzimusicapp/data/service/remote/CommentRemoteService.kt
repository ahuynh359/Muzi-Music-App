package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.CommentAPI
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.request.AddCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.EditCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.ReplyCommentRequest
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseData
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseWithTotalCommentList
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.toCommentList
import com.ahuynh.muzimusicapp.data.model.response.toListAlbum
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class CommentRemoteService @Inject constructor(
    private val commentAPI: CommentAPI
) : BaseRemoteService() {
    suspend fun getAllCommentsOfSong(id: Long): Response<CommentResponseWithTotalCommentList> {
        return callApi { commentAPI.getAllCommentsOfSong(id) }
    }


    suspend fun createComment(commentRequest: AddCommentRequest): Response<CommentResponseData> {
        return callApi { commentAPI.createComment(commentRequest) }
    }

    suspend fun editComment(editCommentRequest: EditCommentRequest): Response<CommentResponseData> {
        return callApi { commentAPI.editComment(editCommentRequest) }
    }

    suspend fun deleteComment(id: Long): Response<MessageResponse> {
        return callApi { commentAPI.deleteComment(id) }
    }

    suspend fun getAllComments(sort: SortName): List<Comment> {
        val result = callApi { commentAPI.getAllComments(sort) }
        return if (result is Response.Success) {
            result.data.data.toCommentList()
        } else {
            arrayListOf()
        }
    }

    suspend fun replyComment(replyCommentRequest: ReplyCommentRequest): Response<CommentResponseData> {
        return callApi { commentAPI.replyComment(replyCommentRequest) }
    }

}



