package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.AddUserRequest
import com.ahuynh.muzimusicapp.data.model.request.ChangePasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.response.CheckLoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponseData
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.UserResponse
import com.ahuynh.muzimusicapp.data.model.response.UserResponseData
import com.ahuynh.muzimusicapp.data.model.response.UserResponseDataList
import com.ahuynh.muzimusicapp.utils.Constants
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAPI {


    @GET("${Constants.API_VERSION}/user/information")
    suspend fun getCurrentUser(
    ): Response<UserResponseData>

    @Multipart
    @PUT("${Constants.API_VERSION}/user/avatar")
    suspend fun changeAvatar(
        @Part avatar: MultipartBody.Part
    ): Response<UserResponseData>

    @PUT("${Constants.API_VERSION}/user/password")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<MessageResponse>

    @GET("${Constants.API_VERSION}/user/all")
    suspend fun getAllUser(): Response<UserResponseDataList>

    @GET("${Constants.API_VERSION}/user/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<UserResponseData>

    @POST("${Constants.API_VERSION}/user/create")
    suspend fun createUser(@Body addUserRequest: AddUserRequest): Response<UserResponseData>


}