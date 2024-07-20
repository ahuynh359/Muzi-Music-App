package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.UpdateAlbumRequest
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponseData
import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.Constants
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumAPI {


    @GET("${Constants.API_VERSION}/album/{id}")
    suspend fun getAlbumById(@Path("id") id: Long): Response<AlbumResponseData>

    @GET("${Constants.API_VERSION}/album/all")
    suspend fun getAllAlbums(@Query("sort") sortName: SortName): Response<AlbumResponseDataList>

    @GET("${Constants.API_VERSION}/album/{id}/songs")
    suspend fun getSongsFromAlbum(@Path("id") id: Long): Response<SongResponseDataList>

    @Multipart
    @POST("${Constants.API_VERSION}/album/create")
    suspend fun createAlbum(@Query("name") name : String, @Part avatar: MultipartBody.Part) : Response<AlbumResponseData>

    @DELETE("${Constants.API_VERSION}/album/{id}")
    suspend fun deleteAlbum(@Path("id") id : Long): Response<MessageResponse>

    @PUT("${Constants.API_VERSION}/album")
    suspend fun updateAlbum(@Body updateAlbumRequest  : UpdateAlbumRequest) : Response<AlbumResponseData>

    @Multipart
    @PUT("${Constants.API_VERSION}/album/avatar/{id}")
    suspend fun updateAvatar(@Path("id") id: Long, @Part avatar: MultipartBody.Part) : Response<AlbumResponseData>


}