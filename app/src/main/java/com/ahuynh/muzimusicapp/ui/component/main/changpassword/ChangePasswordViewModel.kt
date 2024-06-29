package com.ahuynh.muzimusicapp.ui.component.main.changpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.request.ChangePasswordRequest
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharePreferencesHelper: SharePreferencesHelper
) : BaseViewModel() {

    var mess: String? = null
    var status = MutableLiveData<Boolean?>(null)
    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val changePasswordRequest = ChangePasswordRequest(
                sharePreferencesHelper.getId(), oldPassword, newPassword, confirmPassword
            )
            val result = userRepository.changePassword(changePasswordRequest)
            if (result is Response.Success) {
                mess = result.data.message

            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            status.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()

    }

}