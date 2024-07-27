package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.AddCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.EditCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.ReplyCommentRequest
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseData
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.CommentResponseWithTotalCommentList
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface CommentAPI {


    @GET("${Constants.API_VERSION}/comment/song/{id}")
    suspend fun getAllCommentsOfSong(
        @Path("id") id: Long
    ): Response<CommentResponseWithTotalCommentList>

    @GET("${Constants.API_VERSION}/comment/all")
    suspend fun getAllComments(
        @Query("sort") sortName: SortName
    ): Response<CommentResponseDataList>

    @POST("${Constants.API_VERSION}/comment/create")
    suspend fun createComment(
        @Body commentRequest: AddCommentRequest
    ): Response<CommentResponseData>

    @PUT("${Constants.API_VERSION}/comment")
    suspend fun editComment(
        @Body editCommentRequest: EditCommentRequest
    ): Response<CommentResponseData>

    @DELETE("${Constants.API_VERSION}/comment/{id}")
    suspend fun deleteComment(
        @Path("id") id: Long
    ): Response<MessageResponse>

    @POST("${Constants.API_VERSION}/comment/reply")
    suspend fun replyComment(
        @Body replyCommentRequest: ReplyCommentRequest
    ): Response<CommentResponseData>


}