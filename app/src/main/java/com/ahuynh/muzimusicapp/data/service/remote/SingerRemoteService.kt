package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.SingerAPI
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.UpdateSingerRequest
import com.ahuynh.muzimusicapp.data.model.response.LoveSingerResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SingerResponseData
import com.ahuynh.muzimusicapp.data.model.response.toListSinger
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.NetworkResult
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
        return if (result is NetworkResult.Success) {
            result.data.data.toListSinger()
        } else {
            arrayListOf()
        }
    }



    suspend fun getSongsOfSinger(id : Long): List<Song> {
        val result = callApi { singerAPI.getSongsOfSinger(id) }
        return if (result is NetworkResult.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }

    suspend fun getLoveSinger(): List<Singer> {
        val result = callApi { singerAPI.getLoveSinger() }
        return if (result is NetworkResult.Success) {
            result.data.data.toListSinger()
        } else {
            arrayListOf()
        }
    }
    suspend fun isUserLoveSinger(id : Long): NetworkResult<LoveSingerResponse> {
       return callApi { singerAPI.isUserLoveSinger(id) }
    }

    suspend fun loveOrUnloveSinger(id: Long) :  NetworkResult<MessageResponse> {
        return callApi { singerAPI.loveOrUnloveSinger(id) }

    }

    suspend fun createSinger(name: String,description: String, avatar: File): NetworkResult<SingerResponseData> {
        val imageFileRequestBody =
            avatar.asRequestBody("image/*".toMediaTypeOrNull())
        return callApi {
            singerAPI.createSinger(
                name,description, MultipartBody.Part.createFormData(
                    "avatar",
                    avatar.name,
                    imageFileRequestBody
                )
            )
        }
    }

    suspend fun updateSinger(updateSingerRequest: UpdateSingerRequest): NetworkResult<SingerResponseData> {
        return callApi { singerAPI.updateSinger(updateSingerRequest) }
    }

    suspend fun deleteSinger(id: Long): NetworkResult<MessageResponse> {
        return callApi { singerAPI.deleteSinger(id) }
    }

    suspend fun changeAvatar(id: Long, file: File) : NetworkResult<SingerResponseData> {
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
        return if (result is NetworkResult.Success) {
            result.data.data.toSinger()
        } else {
            null
        }
    }
}