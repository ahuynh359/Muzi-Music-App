package com.ahuynh.muzimusicapp.ui.component.splash

import androidx.lifecycle.ViewModel
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharePreferencesHelper: SharePreferencesHelper
) : BaseViewModel() {


    fun isLoggedIn() : Boolean{
        return sharePreferencesHelper.isLoggedIn()
    }

    fun isAdmin(): Boolean {
        return sharePreferencesHelper.getAdminOrUser()

    }

}
