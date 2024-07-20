package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.UpdateTypeRequest
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.component.user.search.fragment.SearchFragmentDirections
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


interface TypeAPI {


    @GET("${Constants.API_VERSION}/type/all")
    suspend fun getAllTypes(@Query("sort") sortName: SortName): Response<TypeResponseDataList>

    @GET("${Constants.API_VERSION}/type/{id}/songs")
    suspend fun getSongFromType(@Path("id") id: Long): Response<SongResponseDataList>

    @GET("${Constants.API_VERSION}/type/{id}")
    suspend fun getTypeById(@Path("id") id: Long): Response<TypeResponseData>

    @Multipart
    @POST("${Constants.API_VERSION}/type/create")
    suspend fun createType(
        @Query("name") name: String,
        @Part avatar: MultipartBody.Part
    ): Response<TypeResponseData>


    @DELETE("${Constants.API_VERSION}/type/{id}")
    suspend fun deleteType(@Path("id") id: Long): Response<MessageResponse>


    @PUT("${Constants.API_VERSION}/type")
    suspend fun updateType(
        @Body updateTypeRequest: UpdateTypeRequest
    ): Response<TypeResponseData>

    @Multipart
    @PUT("${Constants.API_VERSION}/type/avatar/{id}")
    suspend fun changeAvatar(
        @Path("id") id: Long,
        @Part avatar: MultipartBody.Part
    ): Response<TypeResponseData>
}