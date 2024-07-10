package com.ahuynh.muzimusicapp.ui.component.admin.type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.AddUserRequest
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ManageTypeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val typeRepository: TypeRepository
) : BaseViewModel() {
    var userList = MutableLiveData<List<User>>()
    var deleteTypeStatus = MutableLiveData<Boolean?>()
    var createTypeStatus = MutableLiveData<Boolean?>()
    var updateTypeStatus = MutableLiveData<Boolean?>()
    var user = MutableLiveData<User>()
    var mess: String? = null
    var avatar = MutableLiveData<String>()
    var typeList = MutableLiveData<List<Type>>()
    var type = MutableLiveData<Type>()

    fun getAllType() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            typeList.postValue(typeRepository.getAllType())

        }
        registerEventParentJobFinish()
    }

    fun createType(name: String, avatar: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = typeRepository.createType(name, avatar)
            if (result is Response.Success) {
                mess = result.data.message

            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            createTypeStatus.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()
    }

    fun getTypeById(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = typeRepository.getTypeById(id)
            result?.let {
                type.postValue(it)
            }

        }
        registerEventParentJobFinish()
    }

    fun updateType(id: Long, str: String, file: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = typeRepository.updateType(id, str, file)
            if (result is Response.Success) {
                mess = result.data.message

            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            updateTypeStatus.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()
    }

    fun deleteType(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = typeRepository.deleteType(id)
            if (result is Response.Success) {
                mess = result.data.message

            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            deleteTypeStatus.postValue(result is Response.Success)
        }

        registerEventParentJobFinish()
    }


}
