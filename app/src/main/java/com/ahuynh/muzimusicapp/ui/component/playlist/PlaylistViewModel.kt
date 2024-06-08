package com.ahuynh.muzimusicapp.ui.component.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.repository.PlaylistRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject
constructor(private val playlistRepository: PlaylistRepository,
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val userRepository: UserRepository
) :
    BaseViewModel() {

    var playlists = MutableLiveData<List<Playlist>>()
    var email = MutableLiveData<String>()
    var status = MutableLiveData<Boolean>(false)
    var mess: String? = null

    fun getAllPlaylist() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            playlists.postValue(playlistRepository.getAllPlaylist(sharePreferencesHelper.getId()))
        }
        registerEventParentJobFinish()
    }

    fun addNewPlaylist(playlist: String) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {

            val id = sharePreferencesHelper.getId()
            val playlistRequest = PlaylistRequest(playlist, id)
            val result = playlistRepository.addPlaylist(playlistRequest)
            if (result is Response.Success) {
                mess = result.data.message
                getAllPlaylist()

            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            status.postValue(true)
        }

        registerEventParentJobFinish()
    }

    fun deletePlaylist(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {

            val result = playlistRepository.deletePlaylist(id)
            if (result is Response.Success) {
                mess = result.data.message
                getAllPlaylist()

            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            status.postValue(true)
        }

        registerEventParentJobFinish()
    }


}