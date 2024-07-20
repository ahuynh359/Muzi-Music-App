package com.ahuynh.muzimusicapp.ui.component.admin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.repository.PlaylistRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject
constructor(
    private val sharePreferencesHelper: SharePreferencesHelper,
) :
    BaseViewModel() {


    fun logout() {
        viewModelScope.launch {
            sharePreferencesHelper.logout()
        }
    }


}