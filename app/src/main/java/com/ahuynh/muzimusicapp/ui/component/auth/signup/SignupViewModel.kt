package com.ahuynh.muzimusicapp.ui.component.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.repository.AuthRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    var mess: String? = null
    var loginStatus = MutableLiveData<Boolean?>(null)

    fun signup(signUpRequest: SignUpRequest) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = authRepository.signup(signUpRequest)
            if (result is NetworkResult.Success) {
                mess = result.data.message
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            loginStatus.postValue(result is NetworkResult.Success)
        }
        registerEventParentJobFinish()


    }


}