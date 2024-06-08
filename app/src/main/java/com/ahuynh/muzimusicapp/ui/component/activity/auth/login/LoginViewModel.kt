package com.ahuynh.muzimusicapp.ui.component.activity.auth.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.response.LoginResponse
import com.ahuynh.muzimusicapp.data.repository.AuthRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val authRepository: AuthRepository
) : BaseViewModel() {
    var mess: String? = null
    var status = MutableLiveData<Boolean?>(null)



    fun login(loginRequest: LoginRequest) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = authRepository.login(loginRequest)
            if (result is Response.Success) {
                mess = result.data.message
                Constants.USER_ID = result.data.data.id
                sharePreferencesHelper.saveLoggedIn(
                    loginRequest.userNameOrEmail,
                    loginRequest.password
                )
                sharePreferencesHelper.saveId(
                    result.data.data.id
                )

                val token = result.data.data.jwt
                sharePreferencesHelper.saveToken(token)
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            status.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()


    }


}