package com.ahuynh.muzimusicapp.ui.component.auth.changepassword

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.request.EmailRequest
import com.ahuynh.muzimusicapp.data.model.request.ResetPasswordRequest
import com.ahuynh.muzimusicapp.data.repository.AuthRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    var mess: String? = null
    var sendEmailStatus = MutableLiveData<Boolean?>(null)
    var changePasswordStatus = MutableLiveData<Boolean?>(null)
    var email: String = ""

    var timeLeftInMillis = MutableLiveData<Long>()
    private var isTimerRunning = false

    private var countDownTimer: CountDownTimer? = null


    fun startTimer() {
        if (isTimerRunning) return
        isTimerRunning = true
        countDownTimer = object : CountDownTimer(timeLeftInMillis.value!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis.value = millisUntilFinished
            }

            override fun onFinish() {
                timeLeftInMillis.value = 0
                isTimerRunning = false
            }
        }.start()
    }

    fun resetTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
        timeLeftInMillis.value = 60000 // Reset lại 1 phút
        startTimer()
    }

    fun resumeTimer() {
        startTimer()
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
        isTimerRunning = false
    }
    fun changePassword(resetPasswordRequest: ResetPasswordRequest) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = authRepository.changePassword(resetPasswordRequest)
            if (result is NetworkResult.Success) {
                mess = result.data.message
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            changePasswordStatus.postValue(result is NetworkResult.Success)
        }
        registerEventParentJobFinish()

    }

    fun sendEmail() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val emailRequest = EmailRequest(email)
            val result = authRepository.sendEmail(emailRequest)
            if (result is NetworkResult.Success) {
                mess = result.data.message
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            sendEmailStatus.postValue(result is NetworkResult.Success)
        }
        registerEventParentJobFinish()
    }
}