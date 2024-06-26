package com.ahuynh.muzimusicapp.ui.component.auth.changepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.request.ResetPasswordRequest
import com.ahuynh.muzimusicapp.data.repository.AuthRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    var mess: String? = null
    var status = MutableLiveData<Boolean?>(null)

    fun changePassword(resetPasswordRequest: ResetPasswordRequest){
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = authRepository.changePassword(resetPasswordRequest)
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