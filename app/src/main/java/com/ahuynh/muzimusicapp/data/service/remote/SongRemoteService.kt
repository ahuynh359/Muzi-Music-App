package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.SongAPI
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.UpdateSongRequest
import com.ahuynh.muzimusicapp.data.model.response.IsLoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SearchResponse
import com.ahuynh.muzimusicapp.data.model.response.SongListen
import com.ahuynh.muzimusicapp.data.model.response.SongResponseData
import com.ahuynh.muzimusicapp.data.model.response.SongResponseDataList
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SongRemoteService @Inject constructor(
    private val songAPI: SongAPI
) : BaseRemoteService() {

    suspend fun getAllSongs(sortName: SortName): List<Song> {
        val result = callApi { songAPI.getAllSongs(sortName) }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }

    suspend fun getTop10(): List<Song> {
        val result = callApi { songAPI.getTop10() }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }

    suspend fun getTop3(): List<SongListen> {
        val result = callApi { songAPI.getTop3() }
        return if (result is Response.Success) {
            result.data.data
        } else {
            arrayListOf()
        }
    }

    suspend fun getSongById(id: Long): Song? {
        val result = callApi { songAPI.getSongById(id) }
        if (result is Response.Success) {
            return result.data.data.toSong()
        } else {
            return null
        }
    }


    suspend fun loveSong(songId: Long): Response<MessageResponse> {
        return callApi { songAPI.loveSong(songId) }
    }

    suspend fun listen(songId: Long): Response<MessageResponse> {
        return callApi { songAPI.listen(songId) }
    }

    suspend fun isUserLoveSong(songId: Long): Response<IsLoveSongResponse> {
        return callApi { songAPI.isUserLoveSong(songId) }
    }

    suspend fun searchSong(str: String): SearchResponse? {
        val result = callApi { songAPI.searchSong(str) }
        return if (result is Response.Success) {
            result.data.data
        } else {
            null
        }
    }

    suspend fun getLoveSong(): Response<LoveSongResponse> {
        return callApi { songAPI.getLoveSong() }
    }

    suspend fun getSongByListen(): List<Song> {
        val result = callApi { songAPI.getSongByListen() }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }

    suspend fun createSong(
        name: String,
        avatar: File,
        file: File,
        lyrics: String,
        albumId: Long,
        singerId: Set<Long>,
        typeId: Set<Long>
    ): Response<SongResponseData> {
        val imageFileRequestBody =
            avatar.asRequestBody("image/*".toMediaTypeOrNull())
        val mp3FileRequestBody =
            file.asRequestBody("audio/*".toMediaTypeOrNull())
        return callApi {
            songAPI.createSong(
                name,
                MultipartBody.Part.createFormData(
                    "avatar",
                    avatar.name,
                    imageFileRequestBody
                ),
                MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    mp3FileRequestBody
                ), lyrics,
                albumId,
                singerId,
                typeId
            )
        }
    }

    suspend fun changeAvatar(id: Long, file: File): Response<SongResponseData> {
        val imageFileRequestBody =
            file.asRequestBody("image/*".toMediaTypeOrNull())
        return callApi {
            songAPI.changeAvatar(
                id, MultipartBody.Part.createFormData(
                    "avatar",
                    file.name,
                    imageFileRequestBody
                )
            )
        }
    }

    suspend fun uploadMusic(id: Long, file: File): Response<SongResponseData> {
        val mp3FileRequestBody =
            file.asRequestBody("audio/*".toMediaTypeOrNull())
        return callApi {
            songAPI.uploadMusic(
                id, MultipartBody.Part.createFormData(
                    "music",
                    file.name,
                    mp3FileRequestBody
                )
            )
        }
    }

    suspend fun updateSong(updateSongRequest: UpdateSongRequest): Response<SongResponseData> {
        return callApi { songAPI.updateSong(updateSongRequest) }

    }

    suspend fun deleteSong(id: Long): Response<MessageResponse> {
        return callApi { songAPI.deleteSong(id) }

    }


}