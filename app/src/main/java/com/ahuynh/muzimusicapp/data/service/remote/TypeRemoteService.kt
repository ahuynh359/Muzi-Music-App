package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.TypeAPI
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.model.response.toListType
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


class TypeRemoteService @Inject constructor(
    private val typeAPI: TypeAPI
) : BaseRemoteService() {
    suspend fun getAllType(): List<Type> {
        val result = callApi { typeAPI.getAllType() }
        return if (result is Response.Success) {
            result.data.data.toListType()
        } else {
            arrayListOf()
        }
    }
    suspend fun getSongFromType(id : Long): List<Song> {
        val result = callApi { typeAPI.getSongFromType(id) }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }

    suspend fun getTypeById(id : Long): Type? {
        val result = callApi { typeAPI.getTypeById(id) }
        return if (result is Response.Success) {
            result.data.data.toType()
        } else {
            null
        }
    }


    suspend fun createType(name : String ,avatar : File): Response<TypeResponseData> {
        val imageFileRequestBody =
            avatar.asRequestBody("image/*".toMediaTypeOrNull())
        return callApi { typeAPI.createType(name, MultipartBody.Part.createFormData(
            "avatar",
            avatar.name,
            imageFileRequestBody
        )) }
    }

    suspend fun updateType(id : Long ,name : String ,avatar : File): Response<TypeResponseData> {
        val imageFileRequestBody =
            avatar.asRequestBody("image/*".toMediaTypeOrNull())
        return callApi { typeAPI.updateType(id,name, MultipartBody.Part.createFormData(
            "avatar",
            avatar.name,
            imageFileRequestBody
        )) }
    }

    suspend fun deleteType(id: Long)  : Response<MessageResponse>{
        return callApi { typeAPI.deleteType(id) }
    }


}