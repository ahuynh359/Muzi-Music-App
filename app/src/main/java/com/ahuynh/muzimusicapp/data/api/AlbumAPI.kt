package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.AlbumRequest
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.response.ListAlbumResponse
import com.ahuynh.muzimusicapp.data.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumAPI {

    //POST =====================
    //Admin Only
    @POST("${Constants.API_VERSION}/album")
    suspend fun addAlbum(@Body albumRequest: AlbumRequest) : Response<ApiResponse>

    //GET =====================
    @GET("${Constants.API_VERSION}/album/get-by-id/{id}")
    suspend fun getAlbumById(@Path("id") id: Long): Response<AlbumResponse>

    @GET("${Constants.API_VERSION}/album/all")
    suspend fun getNewAlbums(): Response<ListAlbumResponse>

    @GET("${Constants.API_VERSION}/album/{id}/songs")
    suspend fun getSongsFromAlbum(@Path("id") id: Long): Response<ListSongResponse>



}