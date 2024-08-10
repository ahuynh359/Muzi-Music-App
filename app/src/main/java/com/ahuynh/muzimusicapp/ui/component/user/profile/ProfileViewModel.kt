package com.ahuynh.muzimusicapp.ui.component.user.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.repository.NotificationRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject
constructor(
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val userRepository: UserRepository,
    private val notificationRepository : NotificationRepository
) :
    BaseViewModel() {

    var currentUser = MutableLiveData<User>()
    var avatar = MutableLiveData<String>()

    var mess: String? = null

    init {
        getUserById()
    }

    fun getUserById() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            currentUser.postValue(userRepository.getUserById(sharePreferencesHelper.getId()))
        }
        registerEventParentJobFinish()
    }


    fun changeAvatar(id: Long, file: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = userRepository.changeAvatar(id, file)
            if (result is Response.Success) {
                avatar.postValue(result.data.data.avatar)
            } else if (result is Response.Failure) {
                mess = result.errorMessage

            }
            registerEventParentJobFinish()


        }

    }


}

