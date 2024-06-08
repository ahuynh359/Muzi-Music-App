package com.ahuynh.muzimusicapp.ui.component.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.repository.PlaylistRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
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
    var user = MutableLiveData<User>()
    init{
        getUserEmailOrPassword()
    }
    fun getUserEmailOrPassword(){
        viewModelScope.launch {
            //email.postValue(sharePreferencesHelper.getEmail())
            //user.postValue(userRepository.getUserByEmail(email.value))
        }
    }





    fun getAllPlaylist(playlistRequest: PlaylistRequest) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            playlists.postValue(playlistRepository.getAllPlaylist(playlistRequest))
        }
        registerEventParentJobFinish()
    }


}