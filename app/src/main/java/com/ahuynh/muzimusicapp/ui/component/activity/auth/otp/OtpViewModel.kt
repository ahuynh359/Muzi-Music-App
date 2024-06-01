package com.ahuynh.muzimusicapp.ui.component.activity.auth.otp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data_api.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data_api.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data_api.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val userRepository: UserRepository
) : BaseViewModel() {


    var mess: String? = ""
    var status = MutableLiveData<Boolean?>(null)

    fun verifyEmail(otp: String) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = userRepository.verifyEmail(otp)
            if (result is Response.Success) {
                mess = result.data.status
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            status.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()


    }


}