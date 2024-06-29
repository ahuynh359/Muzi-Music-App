package com.ahuynh.muzimusicapp.ui.base.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.utils.helper.NetworkConnectivityHelper
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) :
    AppCompatActivity() {
    lateinit var binding: B
    private lateinit var snackbar: Snackbar
    private val networkConnectivityObserver: NetworkConnectivityHelper by lazy {
        NetworkConnectivityHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        snackbar = Snackbar.make(
            getSnackbarView(),
            R.string.no_internet,
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Wifi") {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        networkConnectivityObserver.observe(this) {
            if (it) {
                if (snackbar.isShown)
                    snackbar.dismiss()
            } else
                snackbar.show()
        }
    }

    abstract fun getSnackbarView(): View
}