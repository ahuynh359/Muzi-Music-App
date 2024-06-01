package com.ahuynh.muzimusicapp.ui.component.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ahuynh.muzimusicapp.databinding.ActivitySplashBinding
import com.ahuynh.muzimusicapp.ui.component.activity.auth.AuthActivity
import com.ahuynh.muzimusicapp.ui.component.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SplashActivity"
    }


    private val viewModel by viewModels<SplashViewModel>()
    private lateinit var binding: ActivitySplashBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkIfUserIsAuthenticated()

    }

    private fun checkIfUserIsAuthenticated() {
        if (viewModel.isLoggedIn()) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
            finish()
        }

    }


}