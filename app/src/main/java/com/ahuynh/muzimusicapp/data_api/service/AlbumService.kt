package com.ahuynh.muzimusicapp.data_api.service

import com.ahuynh.muzimusicapp.data_api.api.AlbumAPI
import com.ahuynh.muzimusicapp.data_api.model.Album
import com.ahuynh.muzimusicapp.data_api.model.Song
import com.ahuynh.muzimusicapp.data_api.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListAlbumResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.data_api.model.response.toListAlbum
import com.ahuynh.muzimusicapp.data_api.model.response.toListSong
import com.ahuynh.muzimusicapp.data_api.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class AlbumService @Inject constructor(
    private val albumAPI: AlbumAPI
) : BaseRemoteService() {
    suspend fun getAllAlbum(): List<Album> {
        val result = callApi { albumAPI.getAllAlbum() }
        return if (result is Response.Success) {
            result.data.data.toListAlbum()
        } else {
            emptyList()
        }
    }

    suspend fun getAlbumById(id: Long): Response<AlbumResponse> {
        return callApi { albumAPI.getAlbumById(id) }
    }

    suspend fun getSongsFromAlbum(id: Long): List<Song> {
        val result = callApi { albumAPI.getSongsFromAlbum(id) }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            emptyList()
        }
    }
}