package com.ahuynh.muzimusicapp.ui.component.activity.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data_api.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data_api.repository.AuthRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val authRepository: AuthRepository
) : BaseViewModel() {
    var mess: String? = null
    var status = MutableLiveData<Boolean?>(null)

    fun signup(signUpRequest: SignUpRequest) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = authRepository.signup(signUpRequest)
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