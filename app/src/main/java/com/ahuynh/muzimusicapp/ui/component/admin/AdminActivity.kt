package com.ahuynh.muzimusicapp.ui.component.admin

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.ActivityAdminBinding
import com.ahuynh.muzimusicapp.databinding.ActivityAuthBinding
import com.ahuynh.muzimusicapp.ui.base.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : BaseActivity<ActivityAdminBinding>(ActivityAdminBinding::inflate) {

    companion object {
        const val TAG = "AdminActivity"
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
        NavigationUI.setupWithNavController(binding.btmNavigation,navController)
    }
}