package com.ahuynh.muzimusicapp.ui.component.player

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ahuynh.muzimusicapp.databinding.ActivityPlayerBinding
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.NetworkConnectivityHelper
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PlayerActivity : AppCompatActivity() {
    private lateinit var snackbar: Snackbar
    private lateinit var binding : ActivityPlayerBinding
    private val networkConnectivityObserver: NetworkConnectivityHelper by lazy {
        NetworkConnectivityHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

        snackbar = Snackbar.make(
            binding.main,
            "No Internet Connection",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Wifi") {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
        networkConnectivityObserver.observe(this) {
            when (it) {
                true -> {
                    if (snackbar.isShown) {
                        snackbar.dismiss()
                    }
                }

                else -> {
                    snackbar.show()
                }
            }

        }

    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    fun onAudioSessionIdEvent(event: EventBusModel.AudioSessionIdEvent) {

    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    fun onSongInfo(event: EventBusModel.SongInfoEvent) {
        event.song?.let { song ->
            binding.tvSong.text = song.name
        }

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    fun onMusicPlayingEvent(event: EventBusModel.MusicPlayingEvent) {
        Log.d("ABC", event.isPlaying.toString())

    }

}