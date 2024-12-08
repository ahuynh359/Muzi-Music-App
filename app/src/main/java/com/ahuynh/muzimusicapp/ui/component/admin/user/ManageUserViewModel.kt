package com.ahuynh.muzimusicapp.ui.component.admin.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.AddUserRequest
import com.ahuynh.muzimusicapp.data.model.request.UpdateUserRequest
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.NetworkResult
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ManageUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharePreferencesHelper: SharePreferencesHelper
) : BaseViewModel() {
    var userList = MutableLiveData<List<User>>()
    var deleteUserStatus = MutableLiveData<Boolean?>()
    var createUserStatus = MutableLiveData<Boolean?>()
    var updateUserStatus = MutableLiveData<Boolean?>()
    var getUserByIdStatus = MutableLiveData<Boolean?>()

    var currentUser = MutableLiveData<User>()
    var mess: String? = null
    var avatar = MutableLiveData<String>()
    var sortUser = MutableLiveData<SortName>()
    var lockOrUnlockStatus = MutableLiveData<Boolean?>()

    init {
        getSortUser()
    }

    fun getSortUser(){
        viewModelScope.launch {
            sortUser.postValue(sharePreferencesHelper.isSortUser())
        }
    }
    fun setSortUser(sortName : SortName){
        viewModelScope.launch {
            sortUser.postValue(sortName)
            sharePreferencesHelper.setSortUser(sortName)
        }
    }

    fun getAllUsers() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            userList.postValue(userRepository.getAllUsers(sharePreferencesHelper.isSortUser()))
        }
        registerEventParentJobFinish()
    }

    fun getUserById(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            currentUser.postValue(userRepository.getUserById(id))
        }
        registerEventParentJobFinish()
    }

    fun createUser(addUserRequest: AddUserRequest) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = userRepository.createUser(addUserRequest)
            if (result is NetworkResult.Success) {
                mess = result.data.message
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            createUserStatus.postValue(result is NetworkResult.Success)
        }
        registerEventParentJobFinish()

    }

    fun deleteUser(id: Long) {
        viewModelScope.launch {
            val result = userRepository.deleteUser(id)
            if (result is NetworkResult.Success) {
                mess = result.data.message
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            deleteUserStatus.postValue(result is NetworkResult.Success)
        }
    }

    fun changeAvatar(id: Long, file: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = userRepository.changeAvatar(id, file)
            if (result is NetworkResult.Success) {
                avatar.postValue(result.data.data.avatar)
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message

            }
            registerEventParentJobFinish()


        }

    }

    fun lockOrUnlockUser(id: Long) {
        viewModelScope.launch {
            val result = userRepository.lockOrUnlockUser(id)
            if (result is NetworkResult.Success) {
                mess = result.data.message
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message

            }
            lockOrUnlockStatus.postValue(result is NetworkResult.Success)
        }

    }

    fun updateUser(updateUserRequest: UpdateUserRequest) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = userRepository.updateUser(updateUserRequest)
            if (result is NetworkResult.Success) {
                mess = result.data.message
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message

            }
            updateUserStatus.postValue(result is NetworkResult.Success)
            registerEventParentJobFinish()


        }

    }


}
