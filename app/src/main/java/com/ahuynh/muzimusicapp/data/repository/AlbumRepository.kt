package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data.service.AlbumService
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

    suspend fun getNewAlbums(): List<Album> {
        return withContext(dispatcher) {
            albumService.getNewAlbums()
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