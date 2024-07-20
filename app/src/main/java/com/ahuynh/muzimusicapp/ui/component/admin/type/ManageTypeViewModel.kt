package com.ahuynh.muzimusicapp.ui.component.admin.type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.UpdateTypeRequest
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ManageTypeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val typeRepository: TypeRepository,
    private val sharePreferencesHelper: SharePreferencesHelper
) : BaseViewModel() {
    var deleteTypeStatus = MutableLiveData<Boolean?>()
    var createTypeStatus = MutableLiveData<Boolean?>()
    var updateTypeStatus = MutableLiveData<Boolean?>()
    var user = MutableLiveData<User>()
    var mess: String? = null
    var avatar = MutableLiveData<String>()
    var typeList = MutableLiveData<List<Type>>()
    var type = MutableLiveData<Type>()
    var sortType = MutableLiveData<SortName>()
    init {
        getSortType()
    }

    fun getSortType(){
        viewModelScope.launch {
            sortType.postValue(sharePreferencesHelper.isSortType())
        }
    }
    fun setSortType(sortName : SortName){
        viewModelScope.launch {
            sortType.postValue(sortName)
            sharePreferencesHelper.setSortType(sortName)
        }
    }



    fun getAllTypes() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            typeList.postValue(typeRepository.getAllTypes(sharePreferencesHelper.isSortType()))

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

    fun updateType(id: Long, str: String) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val updateTypeRequest = UpdateTypeRequest(id,str)
            val result = typeRepository.updateType(updateTypeRequest)
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

    fun changeAvatar(id: Long, file: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = typeRepository.changeAvatar(id, file)
            if (result is Response.Success) {
                avatar.postValue(result.data.data.avatar)
            } else if (result is Response.Failure) {
                mess = result.errorMessage

            }
            registerEventParentJobFinish()


        }

    }


}
