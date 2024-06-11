package com.ahuynh.muzimusicapp.ui.component.setting

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
class SettingViewModel @Inject
constructor(private val playlistRepository: PlaylistRepository,
            private val sharePreferencesHelper: SharePreferencesHelper,
            private val userRepository: UserRepository
) :
    BaseViewModel() {

    var playlists = MutableLiveData<List<Playlist>>()
    var email = MutableLiveData<String>()
    var status = MutableLiveData<Boolean>(false)
    var mess: String? = null

    fun logout(){
        viewModelScope.launch {
            sharePreferencesHelper.logout()
        }
    }


}