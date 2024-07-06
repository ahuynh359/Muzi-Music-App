package com.ahuynh.muzimusicapp.ui.component.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ahuynh.muzimusicapp.databinding.ActivitySplashBinding
import com.ahuynh.muzimusicapp.ui.component.auth.AuthActivity
import com.ahuynh.muzimusicapp.ui.component.user.UserActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SplashActivity"
    }


    private val viewModel by viewModels<SplashViewModel>()
    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkIfUserIsAuthenticated()

    }

    private fun checkIfUserIsAuthenticated() {
        if (viewModel.isLoggedIn()) {
            startActivity(Intent(this@SplashActivity, UserActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
            finish()
        }

    }


}