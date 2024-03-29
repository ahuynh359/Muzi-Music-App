package com.ahuynh.muzimusicapp.ui.component.player

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ahuynh.muzimusicapp.databinding.ActivityPlayerBinding
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Constants.ACTION_NEXT
import com.ahuynh.muzimusicapp.utils.Constants.ACTION_PLAY
import com.ahuynh.muzimusicapp.utils.Constants.ACTION_PRE
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.Utils.toTimeFormat
import com.ahuynh.muzimusicapp.utils.NetworkConnectivityHelper
import com.ahuynh.muzimusicapp.utils.VersionHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {
    private lateinit var snackbar: Snackbar
    private lateinit var binding : ActivityPlayerBinding
    private val networkConnectivityObserver: NetworkConnectivityHelper by lazy {
        NetworkConnectivityHelper(this)
    }
    private val viewModel by viewModels<PlayerViewModel>()
    private var isSliderPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get current song, list song, isPlaying or not from service
        EventBus.getDefault().post(EventBusModel.RequestSongEvent())

        setUpSeekbar()

        binding.btnDown.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.isPlaying.observe(this) {
            binding.btnPlayPause.setImageResource(
                if (it) com.ahuynh.muzimusicapp.R.drawable.ic_play
                else com.ahuynh.muzimusicapp.R.drawable.ic_pause
            )
        }





        binding.btnPlayPause.setOnClickListener {

            if (viewModel.isClear) {
                sendMusic(
                    ACTION_PLAY,
                    viewModel.song.value,
                    viewModel.songList.value!!
                )
                viewModel.isClear = false
            } else {
                sendMusic(ACTION_PLAY)
            }
        }

        binding.btnPre.setOnClickListener {
            if (viewModel.isClear) {
                sendMusic(
                    ACTION_PRE,
                    viewModel.song.value,
                    viewModel.songList.value!!
                )
                viewModel.isClear = false
            } else {
                sendMusic(ACTION_PRE)
            }
        }

        binding.btnNext.setOnClickListener {
            if (viewModel.isClear) {
                sendMusic(
                    ACTION_NEXT,
                    viewModel.song.value,
                    viewModel.songList.value!!
                )
                viewModel.isClear = false
            } else {
                sendMusic(ACTION_NEXT)
            }
        }


    }

    fun sendMusic(
        action: Int,
        song: Song? = null,
        songList: ArrayList<Song> = arrayListOf()
    ) {

        val bundle = Bundle().apply {
            putParcelable(Constants.SONG, song)
            putParcelableArrayList(Constants.SONG_LIST, songList)
        }

        val intent = Intent(applicationContext, MusicService::class.java).apply {
            putExtra(Constants.ACTION, action)
            putExtra(Constants.DATA, bundle)
        }

        if (VersionHelper.isO()) {
            this.startForegroundService(intent)
        } else {
            this.startService(intent)
        }

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
            viewModel.song.postValue(song)

            runOnUiThread {
                binding.tvSong.text = song.name
                binding.tvSongName.text = song.name
                binding.tvSinger.text = song.singer

                Glide
                    .with(binding.imvSong.context)
                    .load(song.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(com.ahuynh.muzimusicapp.R.drawable.big_song)
                    .into(binding.imvSong);
            }

        }


    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    fun onMusicPlayingEvent(event: EventBusModel.MusicPlayingEvent) {


        viewModel.isPlaying.postValue(event.isPlaying)

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMusicTimeEvent(event: EventBusModel.MusicTimeEvent) {
        Log.d("PlayerActivity", event.timeMillis.toString())
        if (event.duration > 0) {
            binding.shimmerSlider.stopShimmer()
            binding.slider.visibility = View.VISIBLE
            binding.shimmerSlider.visibility = View.GONE
            binding.tvEndTime.text = (event.duration / 1000).toInt().toTimeFormat()
            if (!isSliderPressed) binding.slider.value = event.timeMillis.toFloat()
            binding.slider.valueTo = event.duration.toFloat()
            binding.tvStartTime.text = (event.timeMillis / 1000).toInt().toTimeFormat()
        } else {
            binding.shimmerSlider.startShimmer()
            binding.tvEndTime.text = "N:/N"
            binding.tvStartTime.text = "00:00"
        }





    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onClearMusic(event: EventBusModel.ClearMusic) {
        viewModel.isClear = true
        viewModel.isPlaying.postValue(false)
        binding.slider.value = 0f


    }
    private fun setUpSeekbar() {
        binding.slider.setLabelFormatter { value: Float ->
            (value / 1000).toInt().toTimeFormat()
        }

        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(p0: Slider) {
                isSliderPressed = true
            }

            override fun onStopTrackingTouch(p0: Slider) {
                EventBus.getDefault()
                    .post(EventBusModel.MusicTimeSeekEvent(binding.slider.value.toLong()))
                isSliderPressed = false

            }

        })
    }


}