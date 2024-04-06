package com.ahuynh.muzimusicapp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {
    protected var parentJob: Job? = null
    var message = MutableLiveData<String?>(null)
    var isLoading = MutableLiveData(false)
        private set

    protected fun registerEventParentJobFinish() {
        parentJob?.invokeOnCompletion { isLoading.postValue(false) }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob?.cancel()
    }
}