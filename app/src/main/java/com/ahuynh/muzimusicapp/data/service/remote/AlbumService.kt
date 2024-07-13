package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.AlbumAPI
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.request.UpdateAlbumRequest
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponseData
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.data.model.response.toListAlbum
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class AlbumService @Inject constructor(
    private val albumAPI: AlbumAPI
) : BaseRemoteService() {
    suspend fun getNewAlbums(): List<Album> {
        val result = callApi { albumAPI.getNewAlbums() }
        return if (result is Response.Success) {
            result.data.data.toListAlbum()
        } else {
            arrayListOf()
        }
    }

    suspend fun getAllAlbums(): List<Album> {
        val result = callApi { albumAPI.getAllAlbum() }
        return if (result is Response.Success) {
            result.data.data.toListAlbum()
        } else {
            arrayListOf()
        }
    }


    suspend fun getAlbumById(id: Long): Album? {
        val result = callApi { albumAPI.getAlbumById(id) }
        return if (result is Response.Success) {
            result.data.data.toAlbum()
        } else {
            null
        }
    }

    suspend fun getSongsFromAlbum(id: Long): List<Song> {
        val result = callApi { albumAPI.getSongsFromAlbum(id) }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }


    suspend fun createAlbum(name: String, avatar: File): Response<AlbumResponseData> {
        val imageFileRequestBody =
            avatar.asRequestBody("image/*".toMediaTypeOrNull())
        return callApi {
            albumAPI.createAlbum(
                name, MultipartBody.Part.createFormData(
                    "avatar",
                    avatar.name,
                    imageFileRequestBody
                )
            )
        }
    }

    suspend fun updateAlbum(updateAlbumRequest: UpdateAlbumRequest): Response<AlbumResponseData> {
        return callApi { albumAPI.updateAlbum(updateAlbumRequest) }
    }

    suspend fun updateAvatar(id : Long, avatar : File): Response<AlbumResponseData> {
        val imageFileRequestBody =
            avatar.asRequestBody("image/*".toMediaTypeOrNull())
        return callApi {
            albumAPI.updateAvatar(
                id, MultipartBody.Part.createFormData(
                    "avatar",
                    avatar.name,
                    imageFileRequestBody
                )
            )
        }
    }

    suspend fun deleteAlbum(id: Long): Response<MessageResponse> {
        return callApi { albumAPI.deleteAlbum(id) }
    }
}