package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.UpdateSongRequest
import com.ahuynh.muzimusicapp.data.model.response.IsLoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.SingerResponseData
import com.ahuynh.muzimusicapp.data.model.response.SongResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.ListSearchResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponseData
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

interface SongAPI {

    @GET("${Constants.API_VERSION}/song/all")
    suspend fun getAllSongs(@Query("sort") sortName: SortName): Response<SongResponseDataList>

    @GET("${Constants.API_VERSION}/song/top10")
    suspend fun getTop10(): Response<SongResponseDataList>

    @GET("${Constants.API_VERSION}/song/{id}")
    suspend fun getSongById(@Path("id") id: Long): Response<SongResponseData>

    @GET("${Constants.API_VERSION}/song/search")
    suspend fun searchSong(@Query("query") query: String): Response<ListSearchResponse>

    @POST("${Constants.API_VERSION}/song/love/{id}")
    suspend fun loveSong(@Path("id") id: Long): Response<MessageResponse>

    @POST("${Constants.API_VERSION}/song/listen/{id}")
    suspend fun listen(@Path("id") id: Long): Response<MessageResponse>

    @GET("${Constants.API_VERSION}/song/is-love-song/{songId}")
    suspend fun isUserLoveSong(@Path("songId") songId: Long): Response<IsLoveSongResponse>

    @GET("${Constants.API_VERSION}/song/love")
    suspend fun getLoveSong(): Response<LoveSongResponse>

    @GET("${Constants.API_VERSION}/song/chart")
    suspend fun getSongByListen(): Response<SongResponseDataList>


    @Multipart
    @POST("${Constants.API_VERSION}/song/create")
    suspend fun createSong(
        @Query("name") name: String,
        @Part avatar: MultipartBody.Part,
        @Part file: MultipartBody.Part,
        @Query("lyrics") lyrics: String,
        @Query("albumId") albumId: Long,
        @Query("singerId") singerId: Set<Long>,
        @Query("typeId") typeId: Set<Long>
    ): Response<SongResponseData>


    @Multipart
    @PUT("${Constants.API_VERSION}/song/avatar/{id}")
    suspend fun changeAvatar(
        @Path("id") id: Long,
        @Part avatar: MultipartBody.Part
    ): Response<SongResponseData>

    @Multipart
    @PUT("${Constants.API_VERSION}/song/music/{id}")
    suspend fun uploadMusic(
        @Path("id") id: Long,
        @Part music: MultipartBody.Part
    ): Response<SongResponseData>

    @PUT("${Constants.API_VERSION}/song/update")
    suspend fun updateSong(@Body updateSongRequest: UpdateSongRequest): Response<SongResponseData>

    @DELETE("${Constants.API_VERSION}/song/{id}")
    suspend fun deleteSong(@Path("id") id: Long): Response<MessageResponse>
}