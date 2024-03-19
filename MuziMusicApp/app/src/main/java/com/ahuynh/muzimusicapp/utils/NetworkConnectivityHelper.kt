package com.ahuynh.muzimusicapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData


class NetworkConnectivityHelper(context: Context) : LiveData<Status>() {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager


    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(Status.Available)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            postValue(Status.Unavailable)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(Status.Unavailable)
        }
    }

    override fun onActive() {
        super.onActive()

        postValue(Status.Unavailable)
        connectivityManager.registerDefaultNetworkCallback(callback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

enum class Status {
    Available, Unavailable
}