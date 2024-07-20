package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.UpdateAlbumRequest
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponseData
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.service.remote.AlbumRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumRemoteService: AlbumRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getAllAlbums(sort : SortName): List<Album> {
        return withContext(dispatcher) {
            albumRemoteService.getAllAlbums(sort)
        }
    }

    suspend fun getAlbumById(id: Long): Album? {
        return withContext(dispatcher) {
            albumRemoteService.getAlbumById(id)
        }
    }

    suspend fun getSongsFromAlbum(id: Long): List<Song> {
        return withContext(dispatcher) {
            albumRemoteService.getSongsFromAlbum(id)
        }
    }

    suspend fun createAlbum(name: String, avatar: File): Response<AlbumResponseData> {

        return withContext(dispatcher) {
            albumRemoteService.createAlbum(name, avatar)
        }
    }

    suspend fun deleteAlbum(id: Long): Response<MessageResponse> {

        return withContext(dispatcher) {
            albumRemoteService.deleteAlbum(id)
        }
    }

    suspend fun updateAlbum(updateAlbumRequest: UpdateAlbumRequest): Response<AlbumResponseData> {

        return withContext(dispatcher) {
            albumRemoteService.updateAlbum(updateAlbumRequest)
        }
    }

    suspend fun updateAvatar(id: Long, avatar: File): Response<AlbumResponseData> {

        return withContext(dispatcher) {
            albumRemoteService.updateAvatar(id, avatar)
        }
    }


}