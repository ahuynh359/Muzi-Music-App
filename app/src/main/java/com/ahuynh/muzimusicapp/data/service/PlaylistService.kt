package com.ahuynh.muzimusicapp.data.service

import com.ahuynh.muzimusicapp.data.api.AlbumAPI
import com.ahuynh.muzimusicapp.data.api.PlaylistAPI
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data.model.response.toListAlbum
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.model.response.toPlaylistResponse
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class PlaylistService @Inject constructor(
    private val playlistAPI: PlaylistAPI
) : BaseRemoteService() {
    suspend fun getAllPlaylist(playlistRequest: PlaylistRequest): List<Playlist> {
        val result = callApi { playlistAPI.getAllPlaylist(playlistRequest) }
        return if (result is Response.Success) {
            result.data.data.toPlaylistResponse()
        } else {
            arrayListOf()
        }
    }

}