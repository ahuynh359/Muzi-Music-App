package com.ahuynh.muzimusicapp.ui.activity.splash

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ahuynh.muzimusicapp.databinding.ActivitySplashBinding
import com.ahuynh.muzimusicapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    companion object {
        const val TAG = "SplashActivity"
    }


    private val viewModel by viewModels<SplashViewModel>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        checkIfUserIsAuthenticated()

    }

    private fun checkIfUserIsAuthenticated() {

    }

    override fun getSnackbarView(): View {
        TODO("Not yet implemented")
    }
}