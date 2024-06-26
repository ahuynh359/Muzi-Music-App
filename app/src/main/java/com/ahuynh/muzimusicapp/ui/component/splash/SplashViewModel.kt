package com.ahuynh.muzimusicapp.ui.component.splash

import androidx.lifecycle.ViewModel
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharePreferencesHelper: SharePreferencesHelper
) : ViewModel() {


    fun isLoggedIn() : Boolean{
        return sharePreferencesHelper.isLoggedIn()
    }

}
