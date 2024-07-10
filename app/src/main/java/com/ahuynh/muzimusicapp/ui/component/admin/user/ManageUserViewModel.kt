package com.ahuynh.muzimusicapp.ui.component.admin.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.AddUserRequest
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ManageUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    var userList = MutableLiveData<List<User>>()
    var deleteUserStatus = MutableLiveData<Boolean?>()
    var createUserStatus = MutableLiveData<Boolean?>()
    var user = MutableLiveData<User>()
    var mess : String ?= null
    var avatar = MutableLiveData<String>()


     fun getAllUser() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            userList.postValue(userRepository.getAllUser())
        }
        registerEventParentJobFinish()
    }

    fun getUserById(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            user.postValue(userRepository.getUserById(id))
        }
        registerEventParentJobFinish()
    }

    fun createUser(addUserRequest: AddUserRequest){
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = userRepository.createUser(addUserRequest)
            if(result is Response.Success){
                mess = result.data.message
            } else if(result is Response.Failure){
                mess = result.errorMessage
            }
            createUserStatus.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()

    }

    fun changeAvatar(file: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = userRepository.changeAvatar( file)
            if (result is Response.Success) {
                avatar.postValue(result.data.data.avatar)
                getAllUser()
            } else if (result is Response.Failure) {
                mess = result.errorMessage

            }
            registerEventParentJobFinish()


        }

    }




}
