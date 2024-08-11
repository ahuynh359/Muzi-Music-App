package com.ahuynh.muzimusicapp.ui.component.admin.song

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.UpdateSongRequest
import com.ahuynh.muzimusicapp.data.repository.AlbumRepository
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.NetworkResult
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ManageSongViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val albumRepository: AlbumRepository,
    private val singerRepository: SingerRepository,
    private val typeRepository: TypeRepository,
) : BaseViewModel() {
    var nameSong = ""
    var lyricsSong = ""
    var userList = MutableLiveData<List<User>>()
    var user = MutableLiveData<User>()
    var mess: String? = null
    var avatar = MutableLiveData<String>()
    var mp3File = MutableLiveData<String>()
    var songList = MutableLiveData<List<Song>>()
    var sortSong = MutableLiveData<SortName>()
    var song = MutableLiveData<Song>()
    var singerIds: MutableSet<Long> = mutableSetOf()
    var albumList = MutableLiveData<List<Album>>()
    var singerName: String = ""
    var singerList = MutableLiveData<List<Singer>>()
    var typeName: String = ""
    var typeList = MutableLiveData<List<Type>>()
    var albumId: Long? = null
    var albumName: String = ""
    var updateSongStatus = MutableLiveData<Boolean?>()
    var deleteSongStatus = MutableLiveData<Boolean?>()
    var typeIds: MutableSet<Long> = mutableSetOf()
    fun getSortSong() {
        viewModelScope.launch {
            sortSong.postValue(sharePreferencesHelper.isSortSong())
        }
    }

    fun setSortSong(sortName: SortName) {
        viewModelScope.launch {
            sortSong.postValue(sortName)
            sharePreferencesHelper.setSortSong(sortName)
        }
    }

    fun getAllSongs() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            songList.postValue(songRepository.getAllSongs(sharePreferencesHelper.isSortSong()))
        }
        registerEventParentJobFinish()
    }


    fun changeAvatar(id: Long, file: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = songRepository.changeAvatar(id, file)
            if (result is NetworkResult.Success) {
                avatar.postValue(result.data.data.avatar)
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message


            }
            registerEventParentJobFinish()


        }

    }

    fun getSongById(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = songRepository.getSongById(id)
            result?.let {
                song.postValue(it)
            }

        }
        registerEventParentJobFinish()
    }

    fun uploadMusic(id: Long, file: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = songRepository.uploadMusic(id, file)
            if (result is NetworkResult.Success) {
                mp3File.postValue(result.data.data.avatar)
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message

            }
            registerEventParentJobFinish()


        }
    }

    fun updateSong(id: Long) {
        parentJob = viewModelScope.launch {
            val updateSongRequest =
                UpdateSongRequest(id, nameSong, lyricsSong, albumId!!, singerIds, typeIds)
            val result = songRepository.updateSong(updateSongRequest)
            if (result is NetworkResult.Success) {
                mess = result.data.message
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message

            }
            updateSongStatus.postValue(result is NetworkResult.Success)
            registerEventParentJobFinish()


        }
    }

    fun getAllAlbums() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            albumList.postValue(albumRepository.getAllAlbums(SortName.A_Z))
        }
        registerEventParentJobFinish()
    }

    fun getAllSingers() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            singerList.postValue(singerRepository.getAllSingers(SortName.A_Z))
        }
        registerEventParentJobFinish()
    }

    fun getAllTypes() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            typeList.postValue(typeRepository.getAllTypes(SortName.A_Z))
        }
        registerEventParentJobFinish()
    }

    fun deleteSong(id: Long) {
        viewModelScope.launch {
            val result = songRepository.deleteSong(id)
            if (result is NetworkResult.Success) {
                mess = result.data.message
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            deleteSongStatus.postValue(result is NetworkResult.Success)
        }
    }


}