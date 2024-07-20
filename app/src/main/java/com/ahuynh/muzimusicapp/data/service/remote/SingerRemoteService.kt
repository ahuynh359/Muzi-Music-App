package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.SingerAPI
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.request.UpdateSingerRequest
import com.ahuynh.muzimusicapp.data.model.request.UpdateTypeRequest
import com.ahuynh.muzimusicapp.data.model.response.LoveSingerResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SingerResponseData
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.data.model.response.toListSinger
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SingerRemoteService @Inject constructor(
    private val singerAPI: SingerAPI
) : BaseRemoteService() {
    suspend fun getAllSingers(sortName: SortName): List<Singer> {
        val result = callApi { singerAPI.getAllSingers(sortName) }
        return if (result is Response.Success) {
            result.data.data.toListSinger()
        } else {
            arrayListOf()
        }
    }

    suspend fun getSongsOfSinger(id : Long): List<Song> {
        val result = callApi { singerAPI.getSongsOfSinger(id) }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }

    suspend fun getLoveSinger(): List<Singer> {
        val result = callApi { singerAPI.getLoveSinger() }
        return if (result is Response.Success) {
            result.data.data.toListSinger()
        } else {
            arrayListOf()
        }
    }
    suspend fun isUserLoveSinger(id : Long): Response<LoveSingerResponse> {
       return callApi { singerAPI.isUserLoveSinger(id) }
    }

    suspend fun loveOrUnloveSinger(id: Long) :  Response<MessageResponse> {
        return callApi { singerAPI.loveOrUnloveSinger(id) }

    }

    suspend fun createSinger(name: String, avatar: File): Response<SingerResponseData> {
        val imageFileRequestBody =
            avatar.asRequestBody("image/*".toMediaTypeOrNull())
        return callApi {
            singerAPI.createSinger(
                name, MultipartBody.Part.createFormData(
                    "avatar",
                    avatar.name,
                    imageFileRequestBody
                )
            )
        }
    }

    suspend fun updateSinger(updateSingerRequest: UpdateSingerRequest): Response<SingerResponseData> {
        return callApi { singerAPI.updateSinger(updateSingerRequest) }
    }

    suspend fun deleteSinger(id: Long): Response<MessageResponse> {
        return callApi { singerAPI.deleteSinger(id) }
    }

    suspend fun changeAvatar(id: Long, file: File) : Response<SingerResponseData> {
        val imageFileRequestBody =
            file.asRequestBody("image/*".toMediaTypeOrNull())
        return callApi {
            singerAPI.changeAvatar(
                id, MultipartBody.Part.createFormData(
                    "avatar",
                    file.name,
                    imageFileRequestBody
                )
            )
        }
    }


    suspend fun getSingerById(id: Long): Singer? {
        val result = callApi { singerAPI.getSingerById(id) }
        return if (result is Response.Success) {
            result.data.data.toSinger()
        } else {
            null
        }
    }
}