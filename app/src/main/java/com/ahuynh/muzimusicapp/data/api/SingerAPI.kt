package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.UpdateSingerRequest
import com.ahuynh.muzimusicapp.data.model.request.UpdateTypeRequest
import com.ahuynh.muzimusicapp.data.model.response.SingerResponseData
import com.ahuynh.muzimusicapp.data.model.response.SongResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.ListSearchResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSingerResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SingerResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
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


interface SingerAPI {

    @GET("${Constants.API_VERSION}/singer/all")
    suspend fun getAllSingers(@Query("sort") sortName: SortName): Response<SingerResponseDataList>



    @GET("${Constants.API_VERSION}/singer/{id}/songs")
    suspend fun getSongsOfSinger(@Path("id") id: Long): Response<SongResponseDataList>

    @GET("${Constants.API_VERSION}/singer/love")
    suspend fun getLoveSinger(): Response<SingerResponseDataList>
    
    @GET("${Constants.API_VERSION}/singer/is-love-singer/{id}")
    suspend fun isUserLoveSinger(@Path("id") id: Long): Response<LoveSingerResponse>

    @POST("${Constants.API_VERSION}/singer/love/{id}")
    suspend fun loveOrUnloveSinger(@Path("id") id: Long): Response<MessageResponse>

    @Multipart
    @POST("${Constants.API_VERSION}/singer/create")
    suspend fun createSinger(
        @Query("name") name: String,
        @Part avatar: MultipartBody.Part
    ): Response<SingerResponseData>


    @DELETE("${Constants.API_VERSION}/singer/{id}")
    suspend fun deleteSinger(@Path("id") id: Long): Response<MessageResponse>


    @PUT("${Constants.API_VERSION}/singer")
    suspend fun updateSinger(
        @Body updateSingerRequest: UpdateSingerRequest
    ): Response<SingerResponseData>

    @Multipart
    @PUT("${Constants.API_VERSION}/singer/avatar/{id}")
    suspend fun changeAvatar(
        @Path("id") id: Long,
        @Part avatar: MultipartBody.Part
    ): Response<SingerResponseData>

    @GET("${Constants.API_VERSION}/singer/{id}")
    suspend fun getSingerById(@Path("id") id: Long): Response<SingerResponseData>

}