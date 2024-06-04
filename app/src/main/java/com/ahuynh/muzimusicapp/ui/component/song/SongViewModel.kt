package com.ahuynh.muzimusicapp.ui.component.song

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.SongOld
import com.ahuynh.muzimusicapp.data.model.playlist.Playlist
import com.ahuynh.muzimusicapp.data.repository.PlaylistRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.data_api.model.Album
import com.ahuynh.muzimusicapp.data_api.model.Song
import com.ahuynh.muzimusicapp.data_api.repository.AlbumRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val playlistRepository: PlaylistRepository,
    private val albumRepository: AlbumRepository,
    private val songRepository: com.ahuynh.muzimusicapp.data_api.repository.SongRepository
) : BaseViewModel() {

    var albumList = MutableLiveData<List<Album>>()
    var songList = MutableLiveData<List<Song>>()
    var deleteSongFromPlaylist = MutableLiveData<Boolean>()
    var listenSongListOld = MutableLiveData<List<SongOld>>()

    var songOfAlbum = MutableLiveData<List<Song>>()

    var deleteSong = MutableLiveData<Boolean>()
    var loveSong = MutableLiveData<Boolean>()

    var sortIndex = MutableLiveData<Int>(-1)
    var getNotification = MutableLiveData<Int>()





    init {
        getAllSongs()
        getAllAlbum()

    }

    fun getUnreadNoti() {

        viewModelScope.launch {
            getNotification.postValue(sharePreferencesHelper.getUnreadNoti())

        }

    }


    fun deleteSongFromPlaylist(playlist: Playlist, songOld: SongOld) {
        isLoading.postValue(true)
        viewModelScope.launch {
            val response = playlistRepository.deleteSongFromPlaylist(playlist, songOld)
            if (response is Response.Success) {
                deleteSongFromPlaylist.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }



    fun getAllSongs() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            songList.postValue(songRepository.getAllSong())
        }
        registerEventParentJobFinish()
    }

    fun getSongOfAlbum(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            songOfAlbum.postValue(albumRepository.getSongsFromAlbum(id))
        }
        registerEventParentJobFinish()
    }


    fun getAllAlbum() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            albumList.postValue(albumRepository.getAllAlbum())
        }
        registerEventParentJobFinish()
    }













}