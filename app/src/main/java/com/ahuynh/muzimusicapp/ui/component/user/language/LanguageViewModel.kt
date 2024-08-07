package com.ahuynh.muzimusicapp.ui.component.user.language

import android.icu.util.ULocale.getLanguage
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val sharePreferencesHelper: SharePreferencesHelper,
) : BaseViewModel() {
    var language = MutableLiveData<String>()

    init {
        getLanguage()
    }

    private fun getLanguage() {
        viewModelScope.launch {
            language.postValue(sharePreferencesHelper.getLanguage())
        }
    }

    fun changeLanguage(selectedLanguage: String) {
        viewModelScope.launch {
            sharePreferencesHelper.setLanguage(selectedLanguage)
            language.postValue(selectedLanguage)
        }


    }


}

