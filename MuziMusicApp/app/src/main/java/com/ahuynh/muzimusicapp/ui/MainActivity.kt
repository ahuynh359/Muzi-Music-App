package com.ahuynh.muzimusicapp.ui

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.ActivityMainBinding
import com.ahuynh.muzimusicapp.utils.Constants.PERMISSION_REQUEST_ID
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.Utils.checkSinglePermissionAny
import com.ahuynh.muzimusicapp.utils.Utils.warningPermissionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding :  ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermission()
        setUpNavigationGraph()
    }

    private fun requestPermission() {
        if (checkSinglePermissionAny(
                this, Manifest.permission.POST_NOTIFICATIONS,
                PERMISSION_REQUEST_ID
            )
        ) {
            Toast.makeText(this@MainActivity, "Granted", Toast.LENGTH_LONG).show()
        }
    }

    //Override method to handle permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ID && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this@MainActivity, "Granted", Toast.LENGTH_LONG).show()
        } else {
            //permission is permanently denied
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                Utils.appSettingOpen(this)
            } else {
                // first time when user deny
                showWarningDialog()
            }
        }

    }

    private fun showWarningDialog(){
        warningPermissionDialog(this) { _: DialogInterface?, which: Int ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    if (checkSinglePermissionAny(
                            this,
                            Manifest.permission.POST_NOTIFICATIONS,
                            PERMISSION_REQUEST_ID
                        )
                    ) {
                        Toast.makeText(this@MainActivity, "Granted", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun setUpNavigationGraph() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.btmNavigation,navController)
    }
}