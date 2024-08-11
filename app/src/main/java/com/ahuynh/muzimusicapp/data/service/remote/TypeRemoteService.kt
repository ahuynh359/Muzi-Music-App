package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.TypeAPI
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.request.UpdateTypeRequest
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.model.response.toListType
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.NetworkResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


class TypeRemoteService @Inject constructor(
    private val typeAPI: TypeAPI
) : BaseRemoteService() {
    suspend fun getAllTypes(sortName: SortName): List<Type> {
        val result = callApi { typeAPI.getAllTypes(sortName) }
        return if (result is NetworkResult.Success) {
            result.data.data.toListType()
        } else {
            arrayListOf()
        }
    }

    suspend fun getSongFromType(id: Long): List<Song> {
        val result = callApi { typeAPI.getSongFromType(id) }
        return if (result is NetworkResult.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }

    suspend fun getTypeById(id: Long): Type? {
        val result = callApi { typeAPI.getTypeById(id) }
        return if (result is NetworkResult.Success) {
            result.data.data.toType()
        } else {
            null
        }
    }


    suspend fun createType(name: String, avatar: File): NetworkResult<TypeResponseData> {
        val imageFileRequestBody =
            avatar.asRequestBody("image/*".toMediaTypeOrNull())
        return callApi {
            typeAPI.createType(
                name, MultipartBody.Part.createFormData(
                    "avatar",
                    avatar.name,
                    imageFileRequestBody
                )
            )
        }
    }

    suspend fun updateType(updateTypeRequest: UpdateTypeRequest): NetworkResult<TypeResponseData> {
        return callApi { typeAPI.updateType(updateTypeRequest) }
    }

    suspend fun deleteType(id: Long): NetworkResult<MessageResponse> {
        return callApi { typeAPI.deleteType(id) }
    }

    suspend fun changeAvatar(id: Long, file: File): NetworkResult<TypeResponseData> {
        val imageFileRequestBody =
            file.asRequestBody("image/*".toMediaTypeOrNull())
        return callApi {
            typeAPI.changeAvatar(
                id, MultipartBody.Part.createFormData(
                    "avatar",
                    file.name,
                    imageFileRequestBody
                )
            )
        }
    }


}