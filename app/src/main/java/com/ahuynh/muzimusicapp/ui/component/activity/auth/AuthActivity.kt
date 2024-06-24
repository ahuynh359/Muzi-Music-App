package com.ahuynh.muzimusicapp.ui.component.activity.auth

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.ActivityAuthBinding
import com.ahuynh.muzimusicapp.databinding.ActivityMainBinding
import com.ahuynh.muzimusicapp.ui.base.BaseActivity
import com.ahuynh.muzimusicapp.ui.component.activity.main.MainViewModel
import com.itextpdf.io.codec.brotli.dec.Dictionary.getData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {

    companion object {
        const val TAG = "AuthActivity"
    }

    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpNavigationGraph()

    }

    override fun getSnackbarView(): View {
        return binding.main
    }

    private fun setUpNavigationGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }
}