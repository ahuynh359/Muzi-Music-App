package com.ahuynh.muzimusicapp.ui.component.activity.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data_api.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data_api.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data_api.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val userRepository: UserRepository
) : BaseViewModel() {
    var mess: String? = ""
    var status = MutableLiveData<Boolean?>(null)

//    fun signup(signUpRequest: SignUpRequest) {
//        isLoading.postValue(true)
//        parentJob = viewModelScope.launch {
//            val result = userRepository.signup(signUpRequest)
//            if (result is Response.Success) {
//                signUpMessage = result.data.message
//            } else if (result is Response.Failure) {
//                signUpMessage = result.errorMessage
//            }
//            signUpStatus.postValue(result is Response.Success)
//        }
//        registerEventParentJobFinish()
//
//
//    }



    fun login(loginRequest: LoginRequest) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = userRepository.login(loginRequest)
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