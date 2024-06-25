package com.ahuynh.muzimusicapp.ui.component.activity.main.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.repository.PlaylistRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File
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
    var avatarFile: File? = null

    fun logout(){
        viewModelScope.launch {
            sharePreferencesHelper.logout()
        }
    }

    fun changeAvatar(file: MultipartBody.Part) {

        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val id = sharePreferencesHelper.getId()
            userRepository.changeAvatar(id, file)
        }
        registerEventParentJobFinish()


    }


}