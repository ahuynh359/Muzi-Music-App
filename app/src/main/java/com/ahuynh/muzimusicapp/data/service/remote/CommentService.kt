package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.CommentAPI
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.AddCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.EditCommentRequest
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data.model.response.CommentResponse
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseData
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.UserResponseData
import com.ahuynh.muzimusicapp.data.model.response.toCommentList
import com.ahuynh.muzimusicapp.data.model.response.toListAlbum
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import javax.inject.Inject

class CommentService @Inject constructor(
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



