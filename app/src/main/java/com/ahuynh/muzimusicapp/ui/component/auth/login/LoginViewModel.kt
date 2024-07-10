package com.ahuynh.muzimusicapp.ui.component.auth.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.repository.AuthRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
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
    var loginStatus = MutableLiveData<Boolean?>(null)


    fun login(loginRequest: LoginRequest) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = authRepository.login(loginRequest)
            if (result is Response.Success) {
                mess = result.data.message
                sharePreferencesHelper.saveLoggedIn(
                    loginRequest.userNameOrEmail,
                    loginRequest.password
                )
                sharePreferencesHelper.saveId(
                    result.data.data.id
                )
                sharePreferencesHelper.saveToken(
                    result.data.data.jwt
                )
                sharePreferencesHelper.setIsAdminOrUser(result.data.data.admin)
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            loginStatus.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()


    }

    fun isAdmin(): Boolean {
        Log.d("ACB",sharePreferencesHelper.getAdminOrUser().toString())
        return sharePreferencesHelper.getAdminOrUser()
    }


}