package com.ahuynh.muzimusicapp.data.service

import com.ahuynh.muzimusicapp.data.api.AlbumAPI
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data.model.response.toListAlbum
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class AlbumService @Inject constructor(
    private val albumAPI: AlbumAPI
) : BaseRemoteService() {
    suspend fun getNewAlbums(): List<Album> {
        val result = callApi { albumAPI.getNewAlbums() }
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