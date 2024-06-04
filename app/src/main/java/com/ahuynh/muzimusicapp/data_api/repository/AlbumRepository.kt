package com.ahuynh.muzimusicapp.data_api.repository

import com.ahuynh.muzimusicapp.data_api.model.Album
import com.ahuynh.muzimusicapp.data_api.model.Song
import com.ahuynh.muzimusicapp.data_api.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.data_api.service.AlbumService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumService: AlbumService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getAllAlbum(): List<Album> {
        return withContext(dispatcher) {
            albumService.getAllAlbum()
        }
    }

    suspend fun getAlbumById(id: Long): Response<AlbumResponse> {
        return withContext(dispatcher) {
            albumService.getAlbumById(id)
        }
    }

    suspend fun getSongsFromAlbum(id: Long): List<Song> {
        return withContext(dispatcher) {
            albumService.getSongsFromAlbum(id)
        }
    }


}